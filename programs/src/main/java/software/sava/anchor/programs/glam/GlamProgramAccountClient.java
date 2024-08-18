package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.types.FundAccount;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public interface GlamProgramAccountClient extends NativeProgramAccountClient {

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts,
                                               final GlamAccounts glamAccounts,
                                               final PublicKey signerPublicKey,
                                               final PublicKey fundPublicKey) {
    return new GlamProgramAccountClientImpl(solanaAccounts, GlamFundAccounts.createAccounts(glamAccounts, signerPublicKey, fundPublicKey));
  }

  static GlamProgramAccountClient createClient(final PublicKey signerPublicKey,
                                               final PublicKey fundPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, signerPublicKey, fundPublicKey);
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchFundAccounts(final SolanaRpcClient rpcClient,
                                                                             final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(programPublicKey, FundAccount.FACTORY);
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchFundAccount(final SolanaRpcClient rpcClient,
                                                                            final String fundName,
                                                                            final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(FundAccount.createNameFilter(fundName)),
        FundAccount.FACTORY
    );
  }

  static Map<PublicKey, FundAccount> filterFundAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey managerPublicKey) {

    return glamFundAccounts.stream()
        .filter(accountInfo -> accountInfo.data().manager().equals(managerPublicKey))
        .collect(Collectors.toMap(AccountInfo::pubKey, AccountInfo::data));
  }

  static Map<PublicKey, FundAccount> filterFundAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey managerPublicKey,
                                                        final String fundName) {
    return glamFundAccounts.stream()
        .filter(accountInfo -> accountInfo.data().name().equals(fundName))
        .filter(accountInfo -> accountInfo.data().manager().equals(managerPublicKey))
        .collect(Collectors.toMap(AccountInfo::pubKey, AccountInfo::data));
  }

  GlamFundAccounts fundAccounts();

  FundPDA createStakeAccountPDA();

  Instruction initializeAndDelegateStake(final FundPDA stakeAccountPDA,
                                         final PublicKey validatorVoteAccount,
                                         final long lamports);
}
