package software.sava.anchor.programs.meteora;

import software.sava.core.accounts.ProgramDerivedAddress;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.programs.Discriminator;

import java.util.List;

import static software.sava.core.accounts.PublicKey.fromBase58Encoded;

public interface MeteoraAccounts {

  MeteoraAccounts MAIN_NET = createAccounts(
      // "LBUZKhRxPF3XUpBCjp4YzTKgLccjZhTSDM9YuVaPwxo",
      "tLBro6JJuZNnpoad3p8pXKohE9f7f7tBZJpaeh6pXt1",
      new int[]{33, 11, 49, 98, 181, 101, 177, 13},
      new int[]{117, 176, 212, 199, 245, 180, 133, 182},
      "Eo7WjKq67rjJQSZxS6z3YkapzY3eMj6Xy8X5EQVn5UaB",
      "FEESngU3neckdwib9X3KWqdL7Mjmqk9XNp3uh5JbP4KP",
      "24Uqj9JCLxUeoC3hGfh5W3s9FM9uCHDS2SG3LYwBpyTi",
      "FarmuwXPWXvefWUeqFAa5w6rifLkq5X6E8bimYvrhCB1",
      "vaU6kP7iNEGkbmPkLmZfGwiGxd4Mob24QQCie5R9kd2",
      "GacY9YuN16HNRTy7ZWwULPccwvfFSBeNLuAQP7y38Du3",
      "MERLuDFBMmsHnsBPZw2sDQZHvXFMwp8EdjudcU2HKky"
  );

  static MeteoraAccounts createAccounts(final PublicKey dlmmProgram,
                                        final Discriminator lbPairDiscriminator,
                                        final Discriminator positionV2Discriminator,
                                        final PublicKey dynamicAmmPoolsProgram,
                                        final PublicKey m3m3StakeForFeeProgram,
                                        final PublicKey vaultProgram,
                                        final PublicKey farmProgram,
                                        final PublicKey dlmmVaultProgram,
                                        final PublicKey affiliateProgram,
                                        final PublicKey mercurialStableSwapProgram) {
    final var eventAuthority = PublicKey.findProgramAddress(
        List.of("__event_authority".getBytes()),
        dlmmProgram
    );
    return new MeteoraAccountsRecord(
        dlmmProgram,
        AccountMeta.createInvoked(dlmmProgram),
        eventAuthority,
        lbPairDiscriminator,
        positionV2Discriminator,
        AccountMeta.createInvoked(dynamicAmmPoolsProgram),
        AccountMeta.createInvoked(m3m3StakeForFeeProgram),
        AccountMeta.createInvoked(vaultProgram),
        AccountMeta.createInvoked(farmProgram),
        AccountMeta.createInvoked(dlmmVaultProgram),
        AccountMeta.createInvoked(affiliateProgram),
        AccountMeta.createInvoked(mercurialStableSwapProgram)
    );
  }

  static MeteoraAccounts createAccounts(final String dlmmProgram,
                                        final int[] lbPairDiscriminator,
                                        final int[] positionV2Discriminator,
                                        final String dynamicAmmPoolsProgram,
                                        final String m3m3StakeForFeeProgram,
                                        final String vaultProgram,
                                        final String farmProgram,
                                        final String dlmmVaultProgram,
                                        final String affiliateProgram,
                                        final String mercurialStableSwapProgram) {
    return createAccounts(
        fromBase58Encoded(dlmmProgram),
        Discriminator.toDiscriminator(lbPairDiscriminator),
        Discriminator.toDiscriminator(positionV2Discriminator),
        fromBase58Encoded(dynamicAmmPoolsProgram),
        fromBase58Encoded(m3m3StakeForFeeProgram),
        fromBase58Encoded(vaultProgram),
        fromBase58Encoded(farmProgram),
        fromBase58Encoded(dlmmVaultProgram),
        fromBase58Encoded(affiliateProgram),
        fromBase58Encoded(mercurialStableSwapProgram)
    );
  }

  PublicKey dlmmProgram();

  AccountMeta invokedDlmmProgram();

  ProgramDerivedAddress eventAuthority();

  Discriminator lbPairDiscriminator();

  Discriminator positionV2Discriminator();

  AccountMeta invokedDynamicAmmPoolsProgram();

  AccountMeta invokedM3m3StakeForFeeProgram();

  AccountMeta invokedVaultProgram();

  AccountMeta invokedFarmProgram();

  AccountMeta invokedDlmmVaultProgram();

  AccountMeta invokedAffiliateProgram();

  AccountMeta invokedMercurialStableSwapProgram();
}
