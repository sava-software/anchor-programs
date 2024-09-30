package software.sava.anchor.programs.glam;

import software.sava.anchor.programs.glam.anchor.types.FundAccount;
import software.sava.anchor.programs.glam.anchor.types.FundModel;
import software.sava.anchor.programs.glam.anchor.types.ShareClassModel;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.tx.Instruction;
import software.sava.rpc.json.http.client.SolanaRpcClient;
import software.sava.rpc.json.http.response.AccountInfo;
import software.sava.solana.programs.clients.NativeProgramAccountClient;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface GlamProgramAccountClient extends NativeProgramAccountClient {

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts, final GlamFundAccounts glamFundAccounts) {
    return new GlamProgramAccountClientImpl(solanaAccounts, glamFundAccounts);
  }

  static GlamProgramAccountClient createClient(final SolanaAccounts solanaAccounts,
                                               final GlamAccounts glamAccounts,
                                               final PublicKey signerPublicKey,
                                               final PublicKey fundPublicKey) {
    return createClient(solanaAccounts, GlamFundAccounts.createAccounts(glamAccounts, signerPublicKey, fundPublicKey));
  }

  static GlamProgramAccountClient createClient(final PublicKey signerPublicKey, final PublicKey fundPublicKey) {
    return createClient(SolanaAccounts.MAIN_NET, GlamAccounts.MAIN_NET, signerPublicKey, fundPublicKey);
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchFundAccounts(final SolanaRpcClient rpcClient,
                                                                             final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(programPublicKey, FundAccount.FACTORY);
  }

  static CompletableFuture<List<AccountInfo<FundAccount>>> fetchFundAccountsByManager(final SolanaRpcClient rpcClient,
                                                                                      final PublicKey managerPublicKey,
                                                                                      final PublicKey programPublicKey) {
    return rpcClient.getProgramAccounts(
        programPublicKey,
        List.of(
            FundAccount.DISCRIMINATOR_FILTER,
            FundAccount.createManagerFilter(managerPublicKey)
        ),
        FundAccount.FACTORY
    );
  }

  static Map<PublicKey, FundAccount> filterFundAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey managerPublicKey) {

    return glamFundAccounts.stream()
        .map(AccountInfo::data)
        .filter(accountInfo -> accountInfo.manager().equals(managerPublicKey))
        .collect(Collectors.toMap(FundAccount::_address, Function.identity()));
  }

  static Map<PublicKey, FundAccount> filterFundAccounts(final List<AccountInfo<FundAccount>> glamFundAccounts,
                                                        final PublicKey managerPublicKey,
                                                        final String fundName) {
    return glamFundAccounts.stream()
        .map(AccountInfo::data)
        .filter(accountInfo -> accountInfo.name().equals(fundName)
            && accountInfo.manager().equals(managerPublicKey))
        .collect(Collectors.toMap(FundAccount::_address, Function.identity()));
  }

  NativeProgramAccountClient delegatedNativeProgramAccountClient();

  GlamFundAccounts fundAccounts();

  Instruction transferLamportsAndSyncNative(final long lamports);

  FundPDA createStakeAccountPDA();

  Instruction splitStakeAccount(final PublicKey existingStakeAccount,
                                final FundPDA newStakeAccountPDA,
                                final long lamports);

  Instruction initializeAndDelegateStake(final FundPDA stakeAccountPDA,
                                         final PublicKey validatorVoteAccount,
                                         final long lamports);

  Instruction initializeFund(final FundModel fundModel);

  Instruction addShareClass(final int shareClassId, final ShareClassModel shareClassModel);

  Instruction updateFund(final FundModel fundModel);

  Instruction closeFund();

  Instruction closeShareClass(final PublicKey shareClassKey, final int shareClassId);

  Instruction subscribe(final PublicKey assetKey,
                        final PublicKey treasuryAssetATAKey,
                        final PublicKey assetATAKey,
                        final int shareClassId,
                        final long amount);

  Instruction redeem(final int shareClassId, final long amount,
                     final boolean inKind);
}
