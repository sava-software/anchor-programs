package software.sava.anchor.programs.flash.perpetuals.anchor;

import java.util.List;

import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddCollateralParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddCollectionParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddCompoundingLiquidityParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddCustodyParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddCustodyToken22AccountParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddInternalOracleParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddLiquidityAndStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddLiquidityParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddMarketParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.AddPoolParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.BurnAndClaimParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.BurnAndStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CancelTriggerOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CancelUnstakeTokenRequestParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CloseAndSwapParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ClosePositionParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CollectRevenueParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CollectStakeRewardParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CollectTokenRewardParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CompoundFeesParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.CreateReferralParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.DecreaseSizeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.DepositStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.DepositTokenStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.DistributeTokenRewardParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.EditLimitOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.EditTriggerOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ExecuteLimitOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ExecuteLimitWithSwapParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ExecuteTriggerOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ExecuteTriggerWithSwapParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetAddCollateralQuoteParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetAddCompoundingLiquidityAmountAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetAddLiquidityAmountAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetAssetsUnderManagementParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetClosePositionQuoteParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetCompoundingTokenDataParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetCompoundingTokenPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetEntryPriceAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetExitPriceAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetLiquidationPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetLiquidationStateParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetLpTokenPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetOraclePriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetPnlParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetPositionDataParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetPositionQuoteParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetRemoveCompoundingLiquidityAmountAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetRemoveLiquidityAmountAndFeeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.GetSwapAmountAndFeesParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.IncreaseSizeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.InitCompoundingParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.InitParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.InitRevenueTokenAccountParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.InitStakingParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.InitTokenVaultParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.LevelUpParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.LiquidateParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.MigrateFlpParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.MigrateStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.OpenPositionParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.PlaceLimitOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.PlaceTriggerOrderParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RefreshStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ReimburseParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveCollateralAndSwapParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveCollateralParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveCompoundingLiquidityParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveCustodyParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveLiquidityParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemoveMarketParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RemovePoolParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.RenameFlpParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.ResizeInternalOracleParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetAdminSignersParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetCustodyConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetCustomOraclePriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetFeeShareParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetFlpStakeConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetInternalCurrentPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetInternalEmaPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetInternalOraclePriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetLpTokenPriceParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetMarketConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetPermissionsParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetPerpetualsConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetPoolConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetProtocolFeeShareParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetTestTimeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetTokenRewardParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetTokenStakeLevelParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SetTokenVaultConfigParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SwapAndAddCollateralParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SwapAndOpenParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SwapFeeInternalParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.SwapParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.TestInitParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UnstakeInstantParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UnstakeRequestParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UnstakeTokenInstantParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UnstakeTokenRequestParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UpdateCustodyParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UpdateTokenRatiosParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.UpdateTradingAccountParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.WithdrawFeesParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.WithdrawInstantFeesParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.WithdrawSolFeesParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.WithdrawStakeParams;
import software.sava.anchor.programs.flash.perpetuals.anchor.types.WithdrawTokenParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class PerpetualsProgram {

  public static final Discriminator INIT_DISCRIMINATOR = toDiscriminator(220, 59, 207, 236, 108, 250, 47, 100);

  public static Instruction init(final AccountMeta invokedPerpetualsProgramMeta,
                                 final PublicKey upgradeAuthorityKey,
                                 final PublicKey multisigKey,
                                 final PublicKey transferAuthorityKey,
                                 final PublicKey perpetualsKey,
                                 final PublicKey perpetualsProgramKey,
                                 final PublicKey perpetualsProgramDataKey,
                                 final PublicKey systemProgramKey,
                                 final PublicKey tokenProgramKey,
                                 final InitParams params) {
    final var keys = List.of(
      createWritableSigner(upgradeAuthorityKey),
      createWrite(multisigKey),
      createWrite(transferAuthorityKey),
      createWrite(perpetualsKey),
      createRead(perpetualsProgramKey),
      createRead(perpetualsProgramDataKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record InitIxData(Discriminator discriminator, InitParams params) implements Borsh {  

    public static InitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 150;

    public static InitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitParams.read(_data, i);
      return new InitIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_POOL_DISCRIMINATOR = toDiscriminator(115, 230, 212, 211, 175, 49, 39, 169);

  public static Instruction addPool(final AccountMeta invokedPerpetualsProgramMeta,
                                    final PublicKey adminKey,
                                    final PublicKey oracleAuthorityKey,
                                    final PublicKey multisigKey,
                                    final PublicKey transferAuthorityKey,
                                    final PublicKey perpetualsKey,
                                    final PublicKey poolKey,
                                    final PublicKey lpTokenMintKey,
                                    final PublicKey metadataAccountKey,
                                    final PublicKey systemProgramKey,
                                    final PublicKey tokenProgramKey,
                                    final PublicKey metadataProgramKey,
                                    final PublicKey rentKey,
                                    final AddPoolParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(oracleAuthorityKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createWrite(perpetualsKey),
      createWrite(poolKey),
      createWrite(lpTokenMintKey),
      createWrite(metadataAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(metadataProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_POOL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddPoolIxData(Discriminator discriminator, AddPoolParams params) implements Borsh {  

    public static AddPoolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddPoolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddPoolParams.read(_data, i);
      return new AddPoolIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator REMOVE_POOL_DISCRIMINATOR = toDiscriminator(132, 42, 53, 138, 28, 220, 170, 55);

  public static Instruction removePool(final AccountMeta invokedPerpetualsProgramMeta,
                                       final PublicKey adminKey,
                                       final PublicKey multisigKey,
                                       final PublicKey transferAuthorityKey,
                                       final PublicKey perpetualsKey,
                                       final PublicKey poolKey,
                                       final PublicKey systemProgramKey,
                                       final RemovePoolParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(transferAuthorityKey),
      createWrite(perpetualsKey),
      createWrite(poolKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_POOL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemovePoolIxData(Discriminator discriminator, RemovePoolParams params) implements Borsh {  

    public static RemovePoolIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static RemovePoolIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemovePoolParams.read(_data, i);
      return new RemovePoolIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_CUSTODY_DISCRIMINATOR = toDiscriminator(247, 254, 126, 17, 26, 6, 215, 117);

  public static Instruction addCustody(final AccountMeta invokedPerpetualsProgramMeta,
                                       final PublicKey adminKey,
                                       final PublicKey multisigKey,
                                       final PublicKey transferAuthorityKey,
                                       final PublicKey perpetualsKey,
                                       final PublicKey poolKey,
                                       final PublicKey custodyKey,
                                       final PublicKey custodyTokenAccountKey,
                                       final PublicKey custodyTokenMintKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey rentKey,
                                       final AddCustodyParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createWrite(custodyTokenAccountKey),
      createRead(custodyTokenMintKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_CUSTODY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddCustodyIxData(Discriminator discriminator, AddCustodyParams params) implements Borsh {  

    public static AddCustodyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static AddCustodyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddCustodyParams.read(_data, i);
      return new AddCustodyIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator ADD_CUSTODY_TOKEN_22_ACCOUNT_DISCRIMINATOR = toDiscriminator(229, 93, 10, 241, 190, 214, 251, 121);

  public static Instruction addCustodyToken22Account(final AccountMeta invokedPerpetualsProgramMeta,
                                                     final PublicKey adminKey,
                                                     final PublicKey multisigKey,
                                                     final PublicKey transferAuthorityKey,
                                                     final PublicKey perpetualsKey,
                                                     final PublicKey poolKey,
                                                     // This account is initialized with the CPI to the token program
                                                     final PublicKey custodyTokenAccountKey,
                                                     final PublicKey custodyTokenMintKey,
                                                     final PublicKey systemProgramKey,
                                                     final PublicKey tokenProgramKey,
                                                     final PublicKey rentKey,
                                                     final AddCustodyToken22AccountParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(custodyTokenAccountKey),
      createRead(custodyTokenMintKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_CUSTODY_TOKEN_22_ACCOUNT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddCustodyToken22AccountIxData(Discriminator discriminator, AddCustodyToken22AccountParams params) implements Borsh {  

    public static AddCustodyToken22AccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static AddCustodyToken22AccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddCustodyToken22AccountParams.read(_data, i);
      return new AddCustodyToken22AccountIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_INTERNAL_ORACLE_DISCRIMINATOR = toDiscriminator(228, 234, 14, 190, 206, 249, 115, 167);

  public static Instruction addInternalOracle(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey adminKey,
                                              final PublicKey multisigKey,
                                              final PublicKey custodyTokenMintKey,
                                              final PublicKey intOracleAccountKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey rentKey,
                                              final AddInternalOracleParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createRead(multisigKey),
      createRead(custodyTokenMintKey),
      createWrite(intOracleAccountKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_INTERNAL_ORACLE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddInternalOracleIxData(Discriminator discriminator, AddInternalOracleParams params) implements Borsh {  

    public static AddInternalOracleIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 44;

    public static AddInternalOracleIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddInternalOracleParams.read(_data, i);
      return new AddInternalOracleIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_COLLECTION_DISCRIMINATOR = toDiscriminator(79, 172, 225, 142, 219, 192, 171, 80);

  public static Instruction addCollection(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey multisigKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey collectionMintKey,
                                          final PublicKey systemProgramKey,
                                          final AddCollectionParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(perpetualsKey),
      createRead(collectionMintKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_COLLECTION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddCollectionIxData(Discriminator discriminator, AddCollectionParams params) implements Borsh {  

    public static AddCollectionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static AddCollectionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddCollectionParams.read(_data, i);
      return new AddCollectionIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_CUSTODY_DISCRIMINATOR = toDiscriminator(143, 229, 131, 48, 248, 212, 167, 185);

  public static Instruction removeCustody(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey receivingAccountKey,
                                          final PublicKey multisigKey,
                                          final PublicKey transferAuthorityKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey poolKey,
                                          final PublicKey custodyKey,
                                          final PublicKey custodyOracleAccountKey,
                                          final PublicKey custodyTokenAccountKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey ixSysvarKey,
                                          final PublicKey receivingTokenMintKey,
                                          final RemoveCustodyParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(receivingAccountKey),
      createWrite(multisigKey),
      createWrite(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(ixSysvarKey),
      createRead(receivingTokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_CUSTODY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveCustodyIxData(Discriminator discriminator, RemoveCustodyParams params) implements Borsh {  

    public static RemoveCustodyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RemoveCustodyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveCustodyParams.read(_data, i);
      return new RemoveCustodyIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator ADD_MARKET_DISCRIMINATOR = toDiscriminator(41, 137, 185, 126, 69, 139, 254, 55);

  public static Instruction addMarket(final AccountMeta invokedPerpetualsProgramMeta,
                                      final PublicKey adminKey,
                                      final PublicKey multisigKey,
                                      final PublicKey transferAuthorityKey,
                                      final PublicKey perpetualsKey,
                                      final PublicKey poolKey,
                                      final PublicKey marketKey,
                                      final PublicKey targetCustodyKey,
                                      final PublicKey collateralCustodyKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey rentKey,
                                      final AddMarketParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(collateralCustodyKey),
      createRead(systemProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_MARKET_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddMarketIxData(Discriminator discriminator, AddMarketParams params) implements Borsh {  

    public static AddMarketIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 22;

    public static AddMarketIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddMarketParams.read(_data, i);
      return new AddMarketIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_MARKET_DISCRIMINATOR = toDiscriminator(138, 35, 250, 163, 200, 202, 40, 110);

  public static Instruction removeMarket(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey multisigKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey marketKey,
                                         final PublicKey targetCustodyKey,
                                         final PublicKey collateralCustodyKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final RemoveMarketParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(collateralCustodyKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_MARKET_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveMarketIxData(Discriminator discriminator, RemoveMarketParams params) implements Borsh {  

    public static RemoveMarketIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static RemoveMarketIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveMarketParams.read(_data, i);
      return new RemoveMarketIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REIMBURSE_DISCRIMINATOR = toDiscriminator(160, 92, 125, 187, 32, 179, 114, 88);

  public static Instruction reimburse(final AccountMeta invokedPerpetualsProgramMeta,
                                      final PublicKey adminKey,
                                      final PublicKey multisigKey,
                                      final PublicKey fundingAccountKey,
                                      final PublicKey perpetualsKey,
                                      final PublicKey poolKey,
                                      final PublicKey custodyKey,
                                      final PublicKey custodyOracleAccountKey,
                                      final PublicKey custodyTokenAccountKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey programKey,
                                      final PublicKey ixSysvarKey,
                                      final PublicKey fundingMintKey,
                                      final ReimburseParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createRead(multisigKey),
      createWrite(fundingAccountKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REIMBURSE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ReimburseIxData(Discriminator discriminator, ReimburseParams params) implements Borsh {  

    public static ReimburseIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static ReimburseIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ReimburseParams.read(_data, i);
      return new ReimburseIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator RESIZE_INTERNAL_ORACLE_DISCRIMINATOR = toDiscriminator(111, 166, 24, 12, 251, 82, 69, 230);

  public static Instruction resizeInternalOracle(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey multisigKey,
                                                 final PublicKey custodyTokenMintKey,
                                                 final PublicKey intOracleAccountKey,
                                                 final PublicKey systemProgramKey,
                                                 final ResizeInternalOracleParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(custodyTokenMintKey),
      createWrite(intOracleAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RESIZE_INTERNAL_ORACLE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ResizeInternalOracleIxData(Discriminator discriminator, ResizeInternalOracleParams params) implements Borsh {  

    public static ResizeInternalOracleIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 40;

    public static ResizeInternalOracleIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ResizeInternalOracleParams.read(_data, i);
      return new ResizeInternalOracleIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_ADMIN_SIGNERS_DISCRIMINATOR = toDiscriminator(240, 171, 141, 105, 124, 2, 225, 188);

  public static Instruction setAdminSigners(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey adminKey,
                                            final PublicKey multisigKey,
                                            final SetAdminSignersParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_ADMIN_SIGNERS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetAdminSignersIxData(Discriminator discriminator, SetAdminSignersParams params) implements Borsh {  

    public static SetAdminSignersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetAdminSignersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetAdminSignersParams.read(_data, i);
      return new SetAdminSignersIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_CUSTODY_CONFIG_DISCRIMINATOR = toDiscriminator(133, 97, 130, 143, 215, 229, 36, 176);

  public static Instruction setCustodyConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey adminKey,
                                             final PublicKey multisigKey,
                                             final PublicKey poolKey,
                                             final PublicKey custodyKey,
                                             final PublicKey systemProgramKey,
                                             final SetCustodyConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_CUSTODY_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetCustodyConfigIxData(Discriminator discriminator, SetCustodyConfigParams params) implements Borsh {  

    public static SetCustodyConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetCustodyConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetCustodyConfigParams.read(_data, i);
      return new SetCustodyConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator SET_PERPETUALS_CONFIG_DISCRIMINATOR = toDiscriminator(80, 72, 21, 191, 29, 121, 45, 111);

  public static Instruction setPerpetualsConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey multisigKey,
                                                final PublicKey perpetualsKey,
                                                final SetPerpetualsConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(perpetualsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_PERPETUALS_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetPerpetualsConfigIxData(Discriminator discriminator, SetPerpetualsConfigParams params) implements Borsh {  

    public static SetPerpetualsConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 143;

    public static SetPerpetualsConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetPerpetualsConfigParams.read(_data, i);
      return new SetPerpetualsConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_PERMISSIONS_DISCRIMINATOR = toDiscriminator(214, 165, 105, 182, 213, 162, 212, 34);

  public static Instruction setPermissions(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey adminKey,
                                           final PublicKey multisigKey,
                                           final PublicKey perpetualsKey,
                                           final SetPermissionsParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createWrite(perpetualsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_PERMISSIONS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetPermissionsIxData(Discriminator discriminator, SetPermissionsParams params) implements Borsh {  

    public static SetPermissionsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 21;

    public static SetPermissionsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetPermissionsParams.read(_data, i);
      return new SetPermissionsIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_POOL_CONFIG_DISCRIMINATOR = toDiscriminator(216, 87, 65, 125, 113, 110, 185, 120);

  public static Instruction setPoolConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey multisigKey,
                                          final PublicKey poolKey,
                                          final PublicKey systemProgramKey,
                                          final SetPoolConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(poolKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_POOL_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetPoolConfigIxData(Discriminator discriminator, SetPoolConfigParams params) implements Borsh {  

    public static SetPoolConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 134;

    public static SetPoolConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetPoolConfigParams.read(_data, i);
      return new SetPoolConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_PROTOCOL_FEE_SHARE_DISCRIMINATOR = toDiscriminator(6, 155, 103, 17, 228, 172, 14, 160);

  public static Instruction setProtocolFeeShare(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey multisigKey,
                                                final PublicKey protocolVaultKey,
                                                final SetProtocolFeeShareParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createWrite(protocolVaultKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_PROTOCOL_FEE_SHARE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetProtocolFeeShareIxData(Discriminator discriminator, SetProtocolFeeShareParams params) implements Borsh {  

    public static SetProtocolFeeShareIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetProtocolFeeShareIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetProtocolFeeShareParams.read(_data, i);
      return new SetProtocolFeeShareIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_MARKET_CONFIG_DISCRIMINATOR = toDiscriminator(128, 237, 216, 59, 122, 62, 156, 30);

  public static Instruction setMarketConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey adminKey,
                                            final PublicKey multisigKey,
                                            final PublicKey marketKey,
                                            final PublicKey targetCustodyKey,
                                            final PublicKey collateralCustodyKey,
                                            final SetMarketConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(collateralCustodyKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_MARKET_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetMarketConfigIxData(Discriminator discriminator, SetMarketConfigParams params) implements Borsh {  

    public static SetMarketConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 21;

    public static SetMarketConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetMarketConfigParams.read(_data, i);
      return new SetMarketConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_FLP_STAKE_CONFIG_DISCRIMINATOR = toDiscriminator(102, 19, 223, 119, 99, 21, 9, 167);

  public static Instruction setFlpStakeConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey adminKey,
                                              final PublicKey multisigKey,
                                              final PublicKey flpStakeAccountKey,
                                              final SetFlpStakeConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(flpStakeAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_FLP_STAKE_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetFlpStakeConfigIxData(Discriminator discriminator, SetFlpStakeConfigParams params) implements Borsh {  

    public static SetFlpStakeConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetFlpStakeConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetFlpStakeConfigParams.read(_data, i);
      return new SetFlpStakeConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_REWARD_DISCRIMINATOR = toDiscriminator(97, 209, 220, 95, 114, 167, 225, 103);

  public static Instruction setTokenReward(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey adminKey,
                                           final PublicKey multisigKey,
                                           final PublicKey tokenVaultKey,
                                           final PublicKey tokenStakeAccountKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final SetTokenRewardParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(tokenVaultKey),
      createWrite(tokenStakeAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_TOKEN_REWARD_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetTokenRewardIxData(Discriminator discriminator, SetTokenRewardParams params) implements Borsh {  

    public static SetTokenRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 20;

    public static SetTokenRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetTokenRewardParams.read(_data, i);
      return new SetTokenRewardIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_STAKE_LEVEL_DISCRIMINATOR = toDiscriminator(74, 184, 65, 143, 136, 165, 178, 6);

  public static Instruction setTokenStakeLevel(final AccountMeta invokedPerpetualsProgramMeta,
                                               final PublicKey adminKey,
                                               final PublicKey multisigKey,
                                               final PublicKey tokenStakeAccountKey,
                                               final SetTokenStakeLevelParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(tokenStakeAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_TOKEN_STAKE_LEVEL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetTokenStakeLevelIxData(Discriminator discriminator, SetTokenStakeLevelParams params) implements Borsh {  

    public static SetTokenStakeLevelIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static SetTokenStakeLevelIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetTokenStakeLevelParams.read(_data, i);
      return new SetTokenStakeLevelIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TOKEN_VAULT_CONFIG_DISCRIMINATOR = toDiscriminator(106, 228, 78, 88, 112, 139, 185, 119);

  public static Instruction setTokenVaultConfig(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey multisigKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey systemProgramKey,
                                                final SetTokenVaultConfigParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(tokenVaultKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_TOKEN_VAULT_CONFIG_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetTokenVaultConfigIxData(Discriminator discriminator, SetTokenVaultConfigParams params) implements Borsh {  

    public static SetTokenVaultConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 75;

    public static SetTokenVaultConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetTokenVaultConfigParams.read(_data, i);
      return new SetTokenVaultConfigIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_FEES_DISCRIMINATOR = toDiscriminator(198, 212, 171, 109, 144, 215, 174, 89);

  public static Instruction withdrawFees(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey adminKey,
                                         final PublicKey multisigKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey protocolVaultKey,
                                         final PublicKey protocolTokenAccountKey,
                                         final PublicKey receivingTokenAccountKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey receivingMintKey,
                                         final WithdrawFeesParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(protocolVaultKey),
      createWrite(protocolTokenAccountKey),
      createWrite(receivingTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(receivingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = WITHDRAW_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record WithdrawFeesIxData(Discriminator discriminator, WithdrawFeesParams params) implements Borsh {  

    public static WithdrawFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static WithdrawFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawFeesParams.read(_data, i);
      return new WithdrawFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_INSTANT_FEES_DISCRIMINATOR = toDiscriminator(210, 236, 193, 124, 205, 149, 255, 203);

  public static Instruction withdrawInstantFees(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey adminKey,
                                                final PublicKey multisigKey,
                                                final PublicKey perpetualsKey,
                                                final PublicKey transferAuthorityKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey tokenVaultTokenAccountKey,
                                                final PublicKey receivingTokenAccountKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey receivingTokenMintKey,
                                                final WithdrawInstantFeesParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(receivingTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(receivingTokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = WITHDRAW_INSTANT_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record WithdrawInstantFeesIxData(Discriminator discriminator, WithdrawInstantFeesParams params) implements Borsh {  

    public static WithdrawInstantFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static WithdrawInstantFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawInstantFeesParams.read(_data, i);
      return new WithdrawInstantFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_SOL_FEES_DISCRIMINATOR = toDiscriminator(191, 53, 166, 97, 124, 212, 228, 219);

  public static Instruction withdrawSolFees(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey adminKey,
                                            final PublicKey multisigKey,
                                            final PublicKey transferAuthorityKey,
                                            final PublicKey perpetualsKey,
                                            final PublicKey receivingAccountKey,
                                            final WithdrawSolFeesParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(receivingAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = WITHDRAW_SOL_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record WithdrawSolFeesIxData(Discriminator discriminator, WithdrawSolFeesParams params) implements Borsh {  

    public static WithdrawSolFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static WithdrawSolFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawSolFeesParams.read(_data, i);
      return new WithdrawSolFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_CUSTODY_DISCRIMINATOR = toDiscriminator(240, 227, 247, 13, 78, 38, 27, 40);

  public static Instruction updateCustody(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey adminKey,
                                          final PublicKey multisigKey,
                                          final PublicKey poolKey,
                                          final PublicKey custodyKey,
                                          final PublicKey systemProgramKey,
                                          final UpdateCustodyParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UPDATE_CUSTODY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UpdateCustodyIxData(Discriminator discriminator, UpdateCustodyParams params) implements Borsh {  

    public static UpdateCustodyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static UpdateCustodyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateCustodyParams.read(_data, i);
      return new UpdateCustodyIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_TOKEN_RATIOS_DISCRIMINATOR = toDiscriminator(214, 110, 250, 128, 137, 112, 57, 219);

  public static Instruction updateTokenRatios(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey adminKey,
                                              final PublicKey multisigKey,
                                              final PublicKey perpetualsKey,
                                              final PublicKey poolKey,
                                              final UpdateTokenRatiosParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(perpetualsKey),
      createWrite(poolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UPDATE_TOKEN_RATIOS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UpdateTokenRatiosIxData(Discriminator discriminator, UpdateTokenRatiosParams params) implements Borsh {  

    public static UpdateTokenRatiosIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateTokenRatiosIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateTokenRatiosParams.read(_data, i);
      return new UpdateTokenRatiosIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator INIT_STAKING_DISCRIMINATOR = toDiscriminator(42, 18, 242, 224, 66, 32, 122, 8);

  public static Instruction initStaking(final AccountMeta invokedPerpetualsProgramMeta,
                                        final PublicKey adminKey,
                                        final PublicKey multisigKey,
                                        final PublicKey transferAuthorityKey,
                                        final PublicKey perpetualsKey,
                                        final PublicKey poolKey,
                                        final PublicKey custodyKey,
                                        final PublicKey lpTokenMintKey,
                                        final PublicKey stakedLpTokenAccountKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey tokenProgramKey,
                                        final PublicKey rentKey,
                                        final InitStakingParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(lpTokenMintKey),
      createWrite(stakedLpTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INIT_STAKING_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record InitStakingIxData(Discriminator discriminator, InitStakingParams params) implements Borsh {  

    public static InitStakingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitStakingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitStakingParams.read(_data, i);
      return new InitStakingIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_COMPOUNDING_DISCRIMINATOR = toDiscriminator(69, 90, 204, 111, 156, 140, 138, 184);

  public static Instruction initCompounding(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey adminKey,
                                            final PublicKey multisigKey,
                                            final PublicKey transferAuthorityKey,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey custodyKey,
                                            final PublicKey lpTokenMintKey,
                                            final PublicKey compoundingVaultKey,
                                            final PublicKey compoundingTokenMintKey,
                                            final PublicKey metadataAccountKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey metadataProgramKey,
                                            final PublicKey rentKey,
                                            final InitCompoundingParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(lpTokenMintKey),
      createWrite(compoundingVaultKey),
      createWrite(compoundingTokenMintKey),
      createWrite(metadataAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(metadataProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INIT_COMPOUNDING_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record InitCompoundingIxData(Discriminator discriminator, InitCompoundingParams params) implements Borsh {  

    public static InitCompoundingIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitCompoundingIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitCompoundingParams.read(_data, i);
      return new InitCompoundingIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator INIT_REVENUE_TOKEN_ACCOUNT_DISCRIMINATOR = toDiscriminator(235, 126, 219, 143, 29, 74, 149, 161);

  public static Instruction initRevenueTokenAccount(final AccountMeta invokedPerpetualsProgramMeta,
                                                    final PublicKey adminKey,
                                                    final PublicKey multisigKey,
                                                    final PublicKey transferAuthorityKey,
                                                    final PublicKey perpetualsKey,
                                                    final PublicKey tokenVaultKey,
                                                    final PublicKey rewardMintKey,
                                                    final PublicKey revenueTokenAccountKey,
                                                    final PublicKey protocolVaultKey,
                                                    final PublicKey protocolTokenAccountKey,
                                                    final PublicKey systemProgramKey,
                                                    final PublicKey tokenProgramKey,
                                                    final PublicKey rentKey,
                                                    final InitRevenueTokenAccountParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(tokenVaultKey),
      createRead(rewardMintKey),
      createWrite(revenueTokenAccountKey),
      createWrite(protocolVaultKey),
      createWrite(protocolTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INIT_REVENUE_TOKEN_ACCOUNT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record InitRevenueTokenAccountIxData(Discriminator discriminator, InitRevenueTokenAccountParams params) implements Borsh {  

    public static InitRevenueTokenAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static InitRevenueTokenAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitRevenueTokenAccountParams.read(_data, i);
      return new InitRevenueTokenAccountIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INIT_TOKEN_VAULT_DISCRIMINATOR = toDiscriminator(203, 26, 194, 169, 252, 226, 179, 180);

  public static Instruction initTokenVault(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey adminKey,
                                           final PublicKey multisigKey,
                                           final PublicKey perpetualsKey,
                                           final PublicKey transferAuthorityKey,
                                           final PublicKey fundingTokenAccountKey,
                                           final PublicKey tokenMintKey,
                                           final PublicKey tokenVaultKey,
                                           final PublicKey tokenVaultTokenAccountKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey rentKey,
                                           final InitTokenVaultParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(fundingTokenAccountKey),
      createRead(tokenMintKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INIT_TOKEN_VAULT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record InitTokenVaultIxData(Discriminator discriminator, InitTokenVaultParams params) implements Borsh {  

    public static InitTokenVaultIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 83;

    public static InitTokenVaultIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = InitTokenVaultParams.read(_data, i);
      return new InitTokenVaultIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_CUSTOM_ORACLE_PRICE_DISCRIMINATOR = toDiscriminator(180, 194, 182, 63, 48, 125, 116, 136);

  public static Instruction setCustomOraclePrice(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey adminKey,
                                                 final PublicKey multisigKey,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey custodyKey,
                                                 final PublicKey oracleAccountKey,
                                                 final PublicKey systemProgramKey,
                                                 final SetCustomOraclePriceParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(custodyKey),
      createWrite(oracleAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_CUSTOM_ORACLE_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetCustomOraclePriceIxData(Discriminator discriminator, SetCustomOraclePriceParams params) implements Borsh {  

    public static SetCustomOraclePriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 44;

    public static SetCustomOraclePriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetCustomOraclePriceParams.read(_data, i);
      return new SetCustomOraclePriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_INTERNAL_CURRENT_PRICE_DISCRIMINATOR = toDiscriminator(187, 242, 45, 203, 214, 7, 211, 213);

  public static Instruction setInternalCurrentPrice(final AccountMeta invokedPerpetualsProgramMeta, final PublicKey authorityKey, final SetInternalCurrentPriceParams params) {
    final var keys = List.of(
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_INTERNAL_CURRENT_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetInternalCurrentPriceIxData(Discriminator discriminator, SetInternalCurrentPriceParams params) implements Borsh {  

    public static SetInternalCurrentPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetInternalCurrentPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetInternalCurrentPriceParams.read(_data, i);
      return new SetInternalCurrentPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator SET_INTERNAL_EMA_PRICE_DISCRIMINATOR = toDiscriminator(46, 30, 57, 7, 225, 198, 92, 164);

  public static Instruction setInternalEmaPrice(final AccountMeta invokedPerpetualsProgramMeta, final PublicKey authorityKey, final SetInternalEmaPriceParams params) {
    final var keys = List.of(
      createWritableSigner(authorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_INTERNAL_EMA_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetInternalEmaPriceIxData(Discriminator discriminator, SetInternalEmaPriceParams params) implements Borsh {  

    public static SetInternalEmaPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetInternalEmaPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetInternalEmaPriceParams.read(_data, i);
      return new SetInternalEmaPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator SET_INTERNAL_ORACLE_PRICE_DISCRIMINATOR = toDiscriminator(174, 160, 15, 168, 232, 159, 122, 204);

  public static Instruction setInternalOraclePrice(final AccountMeta invokedPerpetualsProgramMeta,
                                                   final PublicKey authorityKey,
                                                   final PublicKey perpetualsKey,
                                                   final PublicKey poolKey,
                                                   final PublicKey custodyKey,
                                                   final PublicKey intOracleAccountKey,
                                                   final PublicKey extOracleAccountKey,
                                                   final PublicKey systemProgramKey,
                                                   final SetInternalOraclePriceParams params) {
    final var keys = List.of(
      createWritableSigner(authorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(custodyKey),
      createWrite(intOracleAccountKey),
      createRead(extOracleAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_INTERNAL_ORACLE_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetInternalOraclePriceIxData(Discriminator discriminator, SetInternalOraclePriceParams params) implements Borsh {  

    public static SetInternalOraclePriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 45;

    public static SetInternalOraclePriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetInternalOraclePriceParams.read(_data, i);
      return new SetInternalOraclePriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_LP_TOKEN_PRICE_DISCRIMINATOR = toDiscriminator(216, 188, 199, 41, 70, 236, 202, 226);

  public static Instruction setLpTokenPrice(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey lpTokenMintKey,
                                            final PublicKey ixSysvarKey,
                                            final SetLpTokenPriceParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createWrite(poolKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_LP_TOKEN_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetLpTokenPriceIxData(Discriminator discriminator, SetLpTokenPriceParams params) implements Borsh {  

    public static SetLpTokenPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static SetLpTokenPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetLpTokenPriceParams.read(_data, i);
      return new SetLpTokenPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_FEE_SHARE_DISCRIMINATOR = toDiscriminator(244, 200, 2, 250, 254, 123, 78, 93);

  public static Instruction setFeeShare(final AccountMeta invokedPerpetualsProgramMeta,
                                        final PublicKey adminKey,
                                        final PublicKey multisigKey,
                                        final PublicKey poolKey,
                                        final SetFeeShareParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createWrite(poolKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_FEE_SHARE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetFeeShareIxData(Discriminator discriminator, SetFeeShareParams params) implements Borsh {  

    public static SetFeeShareIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetFeeShareIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetFeeShareParams.read(_data, i);
      return new SetFeeShareIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator TEST_INIT_DISCRIMINATOR = toDiscriminator(48, 51, 92, 122, 81, 19, 112, 41);

  public static Instruction testInit(final AccountMeta invokedPerpetualsProgramMeta,
                                     final PublicKey upgradeAuthorityKey,
                                     final PublicKey multisigKey,
                                     final PublicKey transferAuthorityKey,
                                     final PublicKey perpetualsKey,
                                     final PublicKey systemProgramKey,
                                     final PublicKey tokenProgramKey,
                                     final TestInitParams params) {
    final var keys = List.of(
      createWritableSigner(upgradeAuthorityKey),
      createWrite(multisigKey),
      createWrite(transferAuthorityKey),
      createWrite(perpetualsKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = TEST_INIT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record TestInitIxData(Discriminator discriminator, TestInitParams params) implements Borsh {  

    public static TestInitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 22;

    public static TestInitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = TestInitParams.read(_data, i);
      return new TestInitIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_TEST_TIME_DISCRIMINATOR = toDiscriminator(242, 231, 177, 251, 126, 145, 159, 104);

  public static Instruction setTestTime(final AccountMeta invokedPerpetualsProgramMeta,
                                        final PublicKey adminKey,
                                        final PublicKey multisigKey,
                                        final PublicKey perpetualsKey,
                                        final SetTestTimeParams params) {
    final var keys = List.of(
      createReadOnlySigner(adminKey),
      createWrite(multisigKey),
      createWrite(perpetualsKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SET_TEST_TIME_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SetTestTimeIxData(Discriminator discriminator, SetTestTimeParams params) implements Borsh {  

    public static SetTestTimeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SetTestTimeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SetTestTimeParams.read(_data, i);
      return new SetTestTimeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_FEE_INTERNAL_DISCRIMINATOR = toDiscriminator(16, 2, 202, 40, 46, 57, 4, 63);

  public static Instruction swapFeeInternal(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey ownerKey,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey rewardCustodyKey,
                                            final PublicKey rewardCustodyOracleAccountKey,
                                            final PublicKey rewardCustodyTokenAccountKey,
                                            final PublicKey custodyKey,
                                            final PublicKey custodyOracleAccountKey,
                                            final PublicKey custodyTokenAccountKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final PublicKey ixSysvarKey,
                                            final SwapFeeInternalParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(rewardCustodyTokenAccountKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SWAP_FEE_INTERNAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SwapFeeInternalIxData(Discriminator discriminator, SwapFeeInternalParams params) implements Borsh {  

    public static SwapFeeInternalIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static SwapFeeInternalIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SwapFeeInternalParams.read(_data, i);
      return new SwapFeeInternalIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_DISCRIMINATOR = toDiscriminator(248, 198, 158, 145, 225, 117, 135, 200);

  public static Instruction swap(final AccountMeta invokedPerpetualsProgramMeta,
                                 final PublicKey ownerKey,
                                 final PublicKey fundingAccountKey,
                                 final PublicKey receivingAccountKey,
                                 final PublicKey transferAuthorityKey,
                                 final PublicKey perpetualsKey,
                                 final PublicKey poolKey,
                                 final PublicKey receivingCustodyKey,
                                 final PublicKey receivingCustodyOracleAccountKey,
                                 final PublicKey receivingCustodyTokenAccountKey,
                                 final PublicKey dispensingCustodyKey,
                                 final PublicKey dispensingCustodyOracleAccountKey,
                                 final PublicKey dispensingCustodyTokenAccountKey,
                                 final PublicKey eventAuthorityKey,
                                 final PublicKey programKey,
                                 final PublicKey ixSysvarKey,
                                 final PublicKey fundingMintKey,
                                 final PublicKey fundingTokenProgramKey,
                                 final PublicKey receivingMintKey,
                                 final PublicKey receivingTokenProgramKey,
                                 final SwapParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(fundingAccountKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createWrite(receivingCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingCustodyOracleAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey),
      createRead(fundingTokenProgramKey),
      createRead(receivingMintKey),
      createRead(receivingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SwapIxData(Discriminator discriminator, SwapParams params) implements Borsh {  

    public static SwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static SwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SwapParams.read(_data, i);
      return new SwapIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_AND_ADD_COLLATERAL_DISCRIMINATOR = toDiscriminator(135, 207, 228, 112, 247, 15, 29, 150);

  public static Instruction swapAndAddCollateral(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey ownerKey,
                                                 final PublicKey feePayerKey,
                                                 final PublicKey fundingAccountKey,
                                                 final PublicKey transferAuthorityKey,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey receivingCustodyKey,
                                                 final PublicKey receivingCustodyOracleAccountKey,
                                                 final PublicKey receivingCustodyTokenAccountKey,
                                                 final PublicKey positionKey,
                                                 final PublicKey marketKey,
                                                 final PublicKey targetCustodyKey,
                                                 final PublicKey targetOracleAccountKey,
                                                 final PublicKey collateralCustodyKey,
                                                 final PublicKey collateralOracleAccountKey,
                                                 final PublicKey collateralCustodyTokenAccountKey,
                                                 final PublicKey fundingTokenProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final PublicKey ixSysvarKey,
                                                 final PublicKey fundingMintKey,
                                                 final SwapAndAddCollateralParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createWrite(receivingCustodyTokenAccountKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(fundingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SWAP_AND_ADD_COLLATERAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SwapAndAddCollateralIxData(Discriminator discriminator, SwapAndAddCollateralParams params) implements Borsh {  

    public static SwapAndAddCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static SwapAndAddCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SwapAndAddCollateralParams.read(_data, i);
      return new SwapAndAddCollateralIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SWAP_AND_OPEN_DISCRIMINATOR = toDiscriminator(26, 209, 42, 0, 169, 62, 30, 118);

  public static Instruction swapAndOpen(final AccountMeta invokedPerpetualsProgramMeta,
                                        final PublicKey ownerKey,
                                        final PublicKey feePayerKey,
                                        final PublicKey fundingAccountKey,
                                        final PublicKey transferAuthorityKey,
                                        final PublicKey perpetualsKey,
                                        final PublicKey poolKey,
                                        final PublicKey receivingCustodyKey,
                                        final PublicKey receivingCustodyOracleAccountKey,
                                        final PublicKey receivingCustodyTokenAccountKey,
                                        final PublicKey positionKey,
                                        final PublicKey marketKey,
                                        final PublicKey targetCustodyKey,
                                        final PublicKey targetOracleAccountKey,
                                        final PublicKey collateralCustodyKey,
                                        final PublicKey collateralOracleAccountKey,
                                        final PublicKey collateralCustodyTokenAccountKey,
                                        final PublicKey systemProgramKey,
                                        final PublicKey fundingTokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final PublicKey ixSysvarKey,
                                        final PublicKey fundingMintKey,
                                        final PublicKey collateralMintKey,
                                        final PublicKey collateralTokenProgramKey,
                                        final SwapAndOpenParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createWrite(receivingCustodyTokenAccountKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(fundingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey),
      createRead(collateralMintKey),
      createRead(collateralTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = SWAP_AND_OPEN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record SwapAndOpenIxData(Discriminator discriminator, SwapAndOpenParams params) implements Borsh {  

    public static SwapAndOpenIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 45;

    public static SwapAndOpenIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = SwapAndOpenParams.read(_data, i);
      return new SwapAndOpenIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_AND_SWAP_DISCRIMINATOR = toDiscriminator(147, 164, 185, 240, 155, 33, 165, 125);

  public static Instruction closeAndSwap(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey feePayerKey,
                                         final PublicKey receivingAccountKey,
                                         final PublicKey collateralAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey positionKey,
                                         final PublicKey marketKey,
                                         final PublicKey targetCustodyKey,
                                         final PublicKey targetOracleAccountKey,
                                         final PublicKey collateralCustodyKey,
                                         final PublicKey collateralOracleAccountKey,
                                         final PublicKey collateralCustodyTokenAccountKey,
                                         final PublicKey dispensingCustodyKey,
                                         final PublicKey dispensingOracleAccountKey,
                                         final PublicKey dispensingCustodyTokenAccountKey,
                                         final PublicKey receivingTokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey receivingMintKey,
                                         final PublicKey collateralMintKey,
                                         final PublicKey collateralTokenProgramKey,
                                         final CloseAndSwapParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(receivingAccountKey),
      createWrite(collateralAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingOracleAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(receivingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey),
      createRead(collateralMintKey),
      createRead(collateralTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = CLOSE_AND_SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CloseAndSwapIxData(Discriminator discriminator, CloseAndSwapParams params) implements Borsh {  

    public static CloseAndSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 29;

    public static CloseAndSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CloseAndSwapParams.read(_data, i);
      return new CloseAndSwapIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_AND_STAKE_DISCRIMINATOR = toDiscriminator(147, 224, 159, 3, 162, 147, 199, 244);

  public static Instruction addLiquidityAndStake(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey ownerKey,
                                                 final PublicKey feePayerKey,
                                                 final PublicKey fundingAccountKey,
                                                 final PublicKey transferAuthorityKey,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey custodyKey,
                                                 final PublicKey custodyOracleAccountKey,
                                                 final PublicKey custodyTokenAccountKey,
                                                 final PublicKey lpTokenMintKey,
                                                 final PublicKey flpStakeAccountKey,
                                                 final PublicKey poolStakedLpVaultKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey tokenProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final PublicKey ixSysvarKey,
                                                 final PublicKey fundingMintKey,
                                                 final PublicKey fundingTokenProgramKey,
                                                 final AddLiquidityAndStakeParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createWrite(flpStakeAccountKey),
      createWrite(poolStakedLpVaultKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey),
      createRead(fundingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_LIQUIDITY_AND_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddLiquidityAndStakeIxData(Discriminator discriminator, AddLiquidityAndStakeParams params) implements Borsh {  

    public static AddLiquidityAndStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static AddLiquidityAndStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddLiquidityAndStakeParams.read(_data, i);
      return new AddLiquidityAndStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_LIQUIDITY_DISCRIMINATOR = toDiscriminator(181, 157, 89, 67, 143, 182, 52, 72);

  public static Instruction addLiquidity(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey fundingAccountKey,
                                         final PublicKey lpTokenAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey custodyKey,
                                         final PublicKey custodyOracleAccountKey,
                                         final PublicKey custodyTokenAccountKey,
                                         final PublicKey lpTokenMintKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey fundingMintKey,
                                         final PublicKey fundingTokenProgramKey,
                                         final AddLiquidityParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(fundingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey),
      createRead(fundingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_LIQUIDITY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddLiquidityIxData(Discriminator discriminator, AddLiquidityParams params) implements Borsh {  

    public static AddLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static AddLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddLiquidityParams.read(_data, i);
      return new AddLiquidityIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_COMPOUNDING_LIQUIDITY_DISCRIMINATOR = toDiscriminator(244, 231, 42, 192, 190, 134, 3, 52);

  public static Instruction addCompoundingLiquidity(final AccountMeta invokedPerpetualsProgramMeta,
                                                    final PublicKey ownerKey,
                                                    final PublicKey fundingAccountKey,
                                                    final PublicKey compoundingTokenAccountKey,
                                                    final PublicKey poolCompoundingLpVaultKey,
                                                    final PublicKey transferAuthorityKey,
                                                    final PublicKey perpetualsKey,
                                                    final PublicKey poolKey,
                                                    final PublicKey inCustodyKey,
                                                    final PublicKey inCustodyOracleAccountKey,
                                                    final PublicKey inCustodyTokenAccountKey,
                                                    final PublicKey rewardCustodyKey,
                                                    final PublicKey rewardCustodyOracleAccountKey,
                                                    final PublicKey lpTokenMintKey,
                                                    final PublicKey compoundingTokenMintKey,
                                                    final PublicKey tokenProgramKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey,
                                                    final PublicKey ixSysvarKey,
                                                    final PublicKey fundingMintKey,
                                                    final PublicKey fundingTokenProgramKey,
                                                    final AddCompoundingLiquidityParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(fundingAccountKey),
      createWrite(compoundingTokenAccountKey),
      createWrite(poolCompoundingLpVaultKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(inCustodyKey),
      createRead(inCustodyOracleAccountKey),
      createWrite(inCustodyTokenAccountKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(lpTokenMintKey),
      createWrite(compoundingTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey),
      createRead(fundingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_COMPOUNDING_LIQUIDITY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddCompoundingLiquidityIxData(Discriminator discriminator, AddCompoundingLiquidityParams params) implements Borsh {  

    public static AddCompoundingLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static AddCompoundingLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddCompoundingLiquidityParams.read(_data, i);
      return new AddCompoundingLiquidityIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_LIQUIDITY_DISCRIMINATOR = toDiscriminator(80, 85, 209, 72, 24, 206, 177, 108);

  public static Instruction removeLiquidity(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey ownerKey,
                                            final PublicKey receivingAccountKey,
                                            final PublicKey lpTokenAccountKey,
                                            final PublicKey transferAuthorityKey,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey custodyKey,
                                            final PublicKey custodyOracleAccountKey,
                                            final PublicKey custodyTokenAccountKey,
                                            final PublicKey lpTokenMintKey,
                                            final PublicKey tokenProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final PublicKey ixSysvarKey,
                                            final PublicKey receivingMintKey,
                                            final PublicKey receivingTokenProgramKey,
                                            final RemoveLiquidityParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingAccountKey),
      createWrite(lpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(custodyKey),
      createRead(custodyOracleAccountKey),
      createWrite(custodyTokenAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey),
      createRead(receivingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_LIQUIDITY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveLiquidityIxData(Discriminator discriminator, RemoveLiquidityParams params) implements Borsh {  

    public static RemoveLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static RemoveLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveLiquidityParams.read(_data, i);
      return new RemoveLiquidityIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_TOKEN_STAKE_DISCRIMINATOR = toDiscriminator(105, 77, 29, 66, 28, 35, 183, 10);

  public static Instruction depositTokenStake(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey ownerKey,
                                              final PublicKey feePayerKey,
                                              final PublicKey fundingTokenAccountKey,
                                              final PublicKey perpetualsKey,
                                              final PublicKey tokenVaultKey,
                                              final PublicKey tokenVaultTokenAccountKey,
                                              final PublicKey tokenStakeAccountKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey tokenProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final PublicKey tokenMintKey,
                                              final DepositTokenStakeParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingTokenAccountKey),
      createRead(perpetualsKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = DEPOSIT_TOKEN_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record DepositTokenStakeIxData(Discriminator discriminator, DepositTokenStakeParams params) implements Borsh {  

    public static DepositTokenStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositTokenStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = DepositTokenStakeParams.read(_data, i);
      return new DepositTokenStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DISTRIBUTE_TOKEN_REWARD_DISCRIMINATOR = toDiscriminator(150, 187, 53, 202, 188, 238, 252, 32);

  public static Instruction distributeTokenReward(final AccountMeta invokedPerpetualsProgramMeta,
                                                  final PublicKey adminKey,
                                                  final PublicKey multisigKey,
                                                  final PublicKey perpetualsKey,
                                                  final PublicKey transferAuthorityKey,
                                                  final PublicKey fundingTokenAccountKey,
                                                  final PublicKey tokenVaultKey,
                                                  final PublicKey tokenVaultTokenAccountKey,
                                                  final PublicKey tokenProgramKey,
                                                  final PublicKey eventAuthorityKey,
                                                  final PublicKey programKey,
                                                  final PublicKey tokenMintKey,
                                                  final DistributeTokenRewardParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(fundingTokenAccountKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = DISTRIBUTE_TOKEN_REWARD_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record DistributeTokenRewardIxData(Discriminator discriminator, DistributeTokenRewardParams params) implements Borsh {  

    public static DistributeTokenRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 20;

    public static DistributeTokenRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = DistributeTokenRewardParams.read(_data, i);
      return new DistributeTokenRewardIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DEPOSIT_STAKE_DISCRIMINATOR = toDiscriminator(160, 167, 9, 220, 74, 243, 228, 43);

  public static Instruction depositStake(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey feePayerKey,
                                         final PublicKey fundingLpTokenAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey flpStakeAccountKey,
                                         final PublicKey poolStakedLpVaultKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey lpTokenMintKey,
                                         final DepositStakeParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingLpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createWrite(poolStakedLpVaultKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(lpTokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = DEPOSIT_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record DepositStakeIxData(Discriminator discriminator, DepositStakeParams params) implements Borsh {  

    public static DepositStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static DepositStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = DepositStakeParams.read(_data, i);
      return new DepositStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REFRESH_STAKE_DISCRIMINATOR = toDiscriminator(194, 123, 40, 247, 37, 237, 119, 119);

  public static Instruction refreshStake(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey rewardCustodyKey,
                                         final PublicKey feeDistributionTokenAccountKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final RefreshStakeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(rewardCustodyKey),
      createRead(feeDistributionTokenAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REFRESH_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RefreshStakeIxData(Discriminator discriminator, RefreshStakeParams params) implements Borsh {  

    public static RefreshStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static RefreshStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RefreshStakeParams.read(_data, i);
      return new RefreshStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNSTAKE_INSTANT_DISCRIMINATOR = toDiscriminator(119, 27, 161, 139, 21, 78, 130, 66);

  public static Instruction unstakeInstant(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey ownerKey,
                                           final PublicKey perpetualsKey,
                                           final PublicKey poolKey,
                                           final PublicKey flpStakeAccountKey,
                                           final PublicKey rewardCustodyKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final UnstakeInstantParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createWrite(rewardCustodyKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UNSTAKE_INSTANT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UnstakeInstantIxData(Discriminator discriminator, UnstakeInstantParams params) implements Borsh {  

    public static UnstakeInstantIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UnstakeInstantIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UnstakeInstantParams.read(_data, i);
      return new UnstakeInstantIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_STAKE_DISCRIMINATOR = toDiscriminator(153, 8, 22, 138, 105, 176, 87, 66);

  public static Instruction withdrawStake(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey ownerKey,
                                          final PublicKey receivingLpTokenAccountKey,
                                          final PublicKey transferAuthorityKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey poolKey,
                                          final PublicKey flpStakeAccountKey,
                                          final PublicKey poolStakedLpVaultKey,
                                          final PublicKey systemProgramKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final PublicKey lpMintKey,
                                          final WithdrawStakeParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingLpTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createWrite(poolStakedLpVaultKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(lpMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = WITHDRAW_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record WithdrawStakeIxData(Discriminator discriminator, WithdrawStakeParams params) implements Borsh {  

    public static WithdrawStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static WithdrawStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawStakeParams.read(_data, i);
      return new WithdrawStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator WITHDRAW_TOKEN_DISCRIMINATOR = toDiscriminator(136, 235, 181, 5, 101, 109, 57, 81);

  public static Instruction withdrawToken(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey ownerKey,
                                          final PublicKey receivingTokenAccountKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey transferAuthorityKey,
                                          final PublicKey tokenVaultKey,
                                          final PublicKey tokenVaultTokenAccountKey,
                                          final PublicKey tokenStakeAccountKey,
                                          final PublicKey tokenProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final PublicKey tokenMintKey,
                                          final WithdrawTokenParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingTokenAccountKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = WITHDRAW_TOKEN_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record WithdrawTokenIxData(Discriminator discriminator, WithdrawTokenParams params) implements Borsh {  

    public static WithdrawTokenIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static WithdrawTokenIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = WithdrawTokenParams.read(_data, i);
      return new WithdrawTokenIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COLLECT_REVENUE_DISCRIMINATOR = toDiscriminator(87, 96, 211, 36, 240, 43, 246, 87);

  public static Instruction collectRevenue(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey ownerKey,
                                           final PublicKey receivingRevenueAccountKey,
                                           final PublicKey perpetualsKey,
                                           final PublicKey transferAuthorityKey,
                                           final PublicKey tokenVaultKey,
                                           final PublicKey revenueTokenAccountKey,
                                           final PublicKey tokenStakeAccountKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final PublicKey receivingTokenMintKey,
                                           final CollectRevenueParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingRevenueAccountKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(tokenVaultKey),
      createWrite(revenueTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(receivingTokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = COLLECT_REVENUE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CollectRevenueIxData(Discriminator discriminator, CollectRevenueParams params) implements Borsh {  

    public static CollectRevenueIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static CollectRevenueIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CollectRevenueParams.read(_data, i);
      return new CollectRevenueIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COLLECT_STAKE_FEES_DISCRIMINATOR = toDiscriminator(61, 174, 225, 165, 103, 145, 250, 181);

  public static Instruction collectStakeFees(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey ownerKey,
                                             final PublicKey receivingTokenAccountKey,
                                             final PublicKey transferAuthorityKey,
                                             final PublicKey perpetualsKey,
                                             final PublicKey poolKey,
                                             final PublicKey feeCustodyKey,
                                             final PublicKey flpStakeAccountKey,
                                             final PublicKey feeCustodyTokenAccountKey,
                                             final PublicKey systemProgramKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final PublicKey ixSysvarKey,
                                             final PublicKey receivingMintKey,
                                             final CollectStakeRewardParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(feeCustodyKey),
      createWrite(flpStakeAccountKey),
      createWrite(feeCustodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = COLLECT_STAKE_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CollectStakeFeesIxData(Discriminator discriminator, CollectStakeRewardParams params) implements Borsh {  

    public static CollectStakeFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static CollectStakeFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CollectStakeRewardParams.read(_data, i);
      return new CollectStakeFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator COLLECT_TOKEN_REWARD_DISCRIMINATOR = toDiscriminator(115, 9, 132, 251, 3, 78, 40, 40);

  public static Instruction collectTokenReward(final AccountMeta invokedPerpetualsProgramMeta,
                                               final PublicKey ownerKey,
                                               final PublicKey receivingTokenAccountKey,
                                               final PublicKey perpetualsKey,
                                               final PublicKey transferAuthorityKey,
                                               final PublicKey tokenVaultKey,
                                               final PublicKey tokenVaultTokenAccountKey,
                                               final PublicKey tokenStakeAccountKey,
                                               final PublicKey tokenProgramKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final PublicKey tokenMintKey,
                                               final CollectTokenRewardParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingTokenAccountKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = COLLECT_TOKEN_REWARD_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CollectTokenRewardIxData(Discriminator discriminator, CollectTokenRewardParams params) implements Borsh {  

    public static CollectTokenRewardIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static CollectTokenRewardIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CollectTokenRewardParams.read(_data, i);
      return new CollectTokenRewardIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNSTAKE_REQUEST_DISCRIMINATOR = toDiscriminator(50, 86, 156, 73, 149, 78, 163, 134);

  public static Instruction unstakeRequest(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey ownerKey,
                                           final PublicKey perpetualsKey,
                                           final PublicKey poolKey,
                                           final PublicKey flpStakeAccountKey,
                                           final PublicKey systemProgramKey,
                                           final PublicKey tokenProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final UnstakeRequestParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UNSTAKE_REQUEST_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UnstakeRequestIxData(Discriminator discriminator, UnstakeRequestParams params) implements Borsh {  

    public static UnstakeRequestIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UnstakeRequestIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UnstakeRequestParams.read(_data, i);
      return new UnstakeRequestIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNSTAKE_TOKEN_INSTANT_DISCRIMINATOR = toDiscriminator(25, 0, 89, 156, 98, 220, 199, 140);

  public static Instruction unstakeTokenInstant(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey ownerKey,
                                                final PublicKey receivingTokenAccountKey,
                                                final PublicKey perpetualsKey,
                                                final PublicKey transferAuthorityKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey tokenVaultTokenAccountKey,
                                                final PublicKey tokenStakeAccountKey,
                                                final PublicKey tokenProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final PublicKey tokenMintKey,
                                                final UnstakeTokenInstantParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(receivingTokenAccountKey),
      createRead(perpetualsKey),
      createRead(transferAuthorityKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UNSTAKE_TOKEN_INSTANT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UnstakeTokenInstantIxData(Discriminator discriminator, UnstakeTokenInstantParams params) implements Borsh {  

    public static UnstakeTokenInstantIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UnstakeTokenInstantIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UnstakeTokenInstantParams.read(_data, i);
      return new UnstakeTokenInstantIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UNSTAKE_TOKEN_REQUEST_DISCRIMINATOR = toDiscriminator(128, 231, 170, 197, 177, 246, 134, 238);

  public static Instruction unstakeTokenRequest(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey ownerKey,
                                                final PublicKey tokenVaultKey,
                                                final PublicKey tokenStakeAccountKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final UnstakeTokenRequestParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(tokenVaultKey),
      createWrite(tokenStakeAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UNSTAKE_TOKEN_REQUEST_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UnstakeTokenRequestIxData(Discriminator discriminator, UnstakeTokenRequestParams params) implements Borsh {  

    public static UnstakeTokenRequestIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static UnstakeTokenRequestIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UnstakeTokenRequestParams.read(_data, i);
      return new UnstakeTokenRequestIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_FLP_DISCRIMINATOR = toDiscriminator(44, 141, 31, 32, 240, 175, 17, 193);

  public static Instruction migrateFlp(final AccountMeta invokedPerpetualsProgramMeta,
                                       final PublicKey ownerKey,
                                       final PublicKey compoundingTokenAccountKey,
                                       final PublicKey transferAuthorityKey,
                                       final PublicKey perpetualsKey,
                                       final PublicKey poolKey,
                                       final PublicKey flpStakeAccountKey,
                                       final PublicKey rewardCustodyKey,
                                       final PublicKey rewardCustodyOracleAccountKey,
                                       final PublicKey poolStakedLpVaultKey,
                                       final PublicKey poolCompoundingLpVaultKey,
                                       final PublicKey lpTokenMintKey,
                                       final PublicKey compoundingTokenMintKey,
                                       final PublicKey systemProgramKey,
                                       final PublicKey tokenProgramKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final PublicKey ixSysvarKey,
                                       final MigrateFlpParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(compoundingTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(poolStakedLpVaultKey),
      createWrite(poolCompoundingLpVaultKey),
      createWrite(lpTokenMintKey),
      createWrite(compoundingTokenMintKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = MIGRATE_FLP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record MigrateFlpIxData(Discriminator discriminator, MigrateFlpParams params) implements Borsh {  

    public static MigrateFlpIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MigrateFlpIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = MigrateFlpParams.read(_data, i);
      return new MigrateFlpIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_STAKE_DISCRIMINATOR = toDiscriminator(178, 5, 26, 85, 56, 20, 153, 160);

  public static Instruction migrateStake(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey compoundingTokenAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey flpStakeAccountKey,
                                         final PublicKey rewardCustodyKey,
                                         final PublicKey rewardCustodyOracleAccountKey,
                                         final PublicKey poolStakedLpVaultKey,
                                         final PublicKey poolCompoundingLpVaultKey,
                                         final PublicKey lpTokenMintKey,
                                         final PublicKey compoundingTokenMintKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final MigrateStakeParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(compoundingTokenAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(flpStakeAccountKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(poolStakedLpVaultKey),
      createWrite(poolCompoundingLpVaultKey),
      createWrite(lpTokenMintKey),
      createWrite(compoundingTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = MIGRATE_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record MigrateStakeIxData(Discriminator discriminator, MigrateStakeParams params) implements Borsh {  

    public static MigrateStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static MigrateStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = MigrateStakeParams.read(_data, i);
      return new MigrateStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MOVE_PROTOCOL_FEES_DISCRIMINATOR = toDiscriminator(129, 151, 181, 212, 47, 232, 58, 98);

  public static Instruction moveProtocolFees(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey transferAuthorityKey,
                                             final PublicKey perpetualsKey,
                                             final PublicKey tokenVaultKey,
                                             final PublicKey poolKey,
                                             final PublicKey custodyKey,
                                             final PublicKey custodyTokenAccountKey,
                                             final PublicKey revenueTokenAccountKey,
                                             final PublicKey protocolVaultKey,
                                             final PublicKey protocolTokenAccountKey,
                                             final PublicKey tokenProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final PublicKey tokenMintKey) {
    final var keys = List.of(
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(tokenVaultKey),
      createRead(poolKey),
      createWrite(custodyKey),
      createWrite(custodyTokenAccountKey),
      createWrite(revenueTokenAccountKey),
      createWrite(protocolVaultKey),
      createWrite(protocolTokenAccountKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(tokenMintKey)
    );

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, MOVE_PROTOCOL_FEES_DISCRIMINATOR);
  }

  public static final Discriminator COMPOUND_FEES_DISCRIMINATOR = toDiscriminator(133, 54, 141, 29, 83, 112, 130, 197);

  public static Instruction compoundFees(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey poolCompoundingLpVaultKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey rewardCustodyKey,
                                         final PublicKey rewardCustodyOracleAccountKey,
                                         final PublicKey lpTokenMintKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final CompoundFeesParams params) {
    final var keys = List.of(
      createWrite(poolCompoundingLpVaultKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(lpTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = COMPOUND_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CompoundFeesIxData(Discriminator discriminator, CompoundFeesParams params) implements Borsh {  

    public static CompoundFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static CompoundFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CompoundFeesParams.read(_data, i);
      return new CompoundFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_COMPOUNDING_LIQUIDITY_DISCRIMINATOR = toDiscriminator(5, 133, 39, 178, 54, 58, 37, 140);

  public static Instruction removeCompoundingLiquidity(final AccountMeta invokedPerpetualsProgramMeta,
                                                       final PublicKey ownerKey,
                                                       final PublicKey receivingAccountKey,
                                                       final PublicKey compoundingTokenAccountKey,
                                                       final PublicKey poolCompoundingLpVaultKey,
                                                       final PublicKey transferAuthorityKey,
                                                       final PublicKey perpetualsKey,
                                                       final PublicKey poolKey,
                                                       final PublicKey outCustodyKey,
                                                       final PublicKey outCustodyOracleAccountKey,
                                                       final PublicKey outCustodyTokenAccountKey,
                                                       final PublicKey rewardCustodyKey,
                                                       final PublicKey rewardCustodyOracleAccountKey,
                                                       final PublicKey lpTokenMintKey,
                                                       final PublicKey compoundingTokenMintKey,
                                                       final PublicKey tokenProgramKey,
                                                       final PublicKey eventAuthorityKey,
                                                       final PublicKey programKey,
                                                       final PublicKey ixSysvarKey,
                                                       final PublicKey receivingMintKey,
                                                       final PublicKey receivingTokenProgramKey,
                                                       final RemoveCompoundingLiquidityParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingAccountKey),
      createWrite(compoundingTokenAccountKey),
      createWrite(poolCompoundingLpVaultKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(outCustodyKey),
      createRead(outCustodyOracleAccountKey),
      createWrite(outCustodyTokenAccountKey),
      createWrite(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createWrite(lpTokenMintKey),
      createWrite(compoundingTokenMintKey),
      createRead(tokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey),
      createRead(receivingTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_COMPOUNDING_LIQUIDITY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveCompoundingLiquidityIxData(Discriminator discriminator, RemoveCompoundingLiquidityParams params) implements Borsh {  

    public static RemoveCompoundingLiquidityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static RemoveCompoundingLiquidityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveCompoundingLiquidityParams.read(_data, i);
      return new RemoveCompoundingLiquidityIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_TRADING_ACCOUNT_DISCRIMINATOR = toDiscriminator(230, 10, 32, 132, 84, 114, 14, 255);

  public static Instruction updateTradingAccount(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey ownerKey,
                                                 final PublicKey feePayerKey,
                                                 final PublicKey nftTokenAccountKey,
                                                 final PublicKey tradingAccountKey,
                                                 final PublicKey referralAccountKey,
                                                 final UpdateTradingAccountParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createRead(nftTokenAccountKey),
      createWrite(tradingAccountKey),
      createWrite(referralAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = UPDATE_TRADING_ACCOUNT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record UpdateTradingAccountIxData(Discriminator discriminator, UpdateTradingAccountParams params) implements Borsh {  

    public static UpdateTradingAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static UpdateTradingAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = UpdateTradingAccountParams.read(_data, i);
      return new UpdateTradingAccountIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_REFERRAL_DISCRIMINATOR = toDiscriminator(61, 17, 240, 245, 172, 66, 159, 232);

  public static Instruction createReferral(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey ownerKey,
                                           final PublicKey feePayerKey,
                                           final PublicKey tokenStakeAccountKey,
                                           final PublicKey referralAccountKey,
                                           final PublicKey systemProgramKey,
                                           final CreateReferralParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createRead(tokenStakeAccountKey),
      createWrite(referralAccountKey),
      createRead(systemProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = CREATE_REFERRAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CreateReferralIxData(Discriminator discriminator, CreateReferralParams params) implements Borsh {  

    public static CreateReferralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static CreateReferralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CreateReferralParams.read(_data, i);
      return new CreateReferralIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator OPEN_POSITION_DISCRIMINATOR = toDiscriminator(135, 128, 47, 77, 15, 152, 240, 49);

  public static Instruction openPosition(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey feePayerKey,
                                         final PublicKey fundingAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey positionKey,
                                         final PublicKey marketKey,
                                         final PublicKey targetCustodyKey,
                                         final PublicKey targetOracleAccountKey,
                                         final PublicKey collateralCustodyKey,
                                         final PublicKey collateralOracleAccountKey,
                                         final PublicKey collateralCustodyTokenAccountKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey fundingTokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey fundingMintKey,
                                         final OpenPositionParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(fundingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = OPEN_POSITION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record OpenPositionIxData(Discriminator discriminator, OpenPositionParams params) implements Borsh {  

    public static OpenPositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 37;

    public static OpenPositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = OpenPositionParams.read(_data, i);
      return new OpenPositionIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator ADD_COLLATERAL_DISCRIMINATOR = toDiscriminator(127, 82, 121, 42, 161, 176, 249, 206);

  public static Instruction addCollateral(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey ownerKey,
                                          final PublicKey fundingAccountKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey poolKey,
                                          final PublicKey positionKey,
                                          final PublicKey marketKey,
                                          final PublicKey targetCustodyKey,
                                          final PublicKey targetOracleAccountKey,
                                          final PublicKey collateralCustodyKey,
                                          final PublicKey collateralOracleAccountKey,
                                          final PublicKey collateralCustodyTokenAccountKey,
                                          final PublicKey fundingTokenProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final PublicKey ixSysvarKey,
                                          final PublicKey fundingMintKey,
                                          final AddCollateralParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(fundingAccountKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(fundingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = ADD_COLLATERAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record AddCollateralIxData(Discriminator discriminator, AddCollateralParams params) implements Borsh {  

    public static AddCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static AddCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = AddCollateralParams.read(_data, i);
      return new AddCollateralIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_COLLATERAL_AND_SWAP_DISCRIMINATOR = toDiscriminator(197, 216, 82, 134, 173, 128, 23, 62);

  public static Instruction removeCollateralAndSwap(final AccountMeta invokedPerpetualsProgramMeta,
                                                    final PublicKey ownerKey,
                                                    final PublicKey feePayerKey,
                                                    final PublicKey receivingAccountKey,
                                                    final PublicKey collateralAccountKey,
                                                    final PublicKey transferAuthorityKey,
                                                    final PublicKey perpetualsKey,
                                                    final PublicKey poolKey,
                                                    final PublicKey positionKey,
                                                    final PublicKey marketKey,
                                                    final PublicKey targetCustodyKey,
                                                    final PublicKey targetOracleAccountKey,
                                                    final PublicKey collateralCustodyKey,
                                                    final PublicKey collateralOracleAccountKey,
                                                    final PublicKey collateralCustodyTokenAccountKey,
                                                    final PublicKey dispensingCustodyKey,
                                                    final PublicKey dispensingOracleAccountKey,
                                                    final PublicKey dispensingCustodyTokenAccountKey,
                                                    final PublicKey receivingTokenProgramKey,
                                                    final PublicKey eventAuthorityKey,
                                                    final PublicKey programKey,
                                                    final PublicKey ixSysvarKey,
                                                    final PublicKey receivingMintKey,
                                                    final PublicKey collateralMintKey,
                                                    final PublicKey collateralTokenProgramKey,
                                                    final RemoveCollateralAndSwapParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(receivingAccountKey),
      createWrite(collateralAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingOracleAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(receivingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey),
      createRead(collateralMintKey),
      createRead(collateralTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_COLLATERAL_AND_SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveCollateralAndSwapIxData(Discriminator discriminator, RemoveCollateralAndSwapParams params) implements Borsh {  

    public static RemoveCollateralAndSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 24;

    public static RemoveCollateralAndSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveCollateralAndSwapParams.read(_data, i);
      return new RemoveCollateralAndSwapIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator REMOVE_COLLATERAL_DISCRIMINATOR = toDiscriminator(86, 222, 130, 86, 92, 20, 72, 65);

  public static Instruction removeCollateral(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey ownerKey,
                                             final PublicKey receivingAccountKey,
                                             final PublicKey transferAuthorityKey,
                                             final PublicKey perpetualsKey,
                                             final PublicKey poolKey,
                                             final PublicKey positionKey,
                                             final PublicKey marketKey,
                                             final PublicKey targetCustodyKey,
                                             final PublicKey targetOracleAccountKey,
                                             final PublicKey collateralCustodyKey,
                                             final PublicKey collateralOracleAccountKey,
                                             final PublicKey collateralCustodyTokenAccountKey,
                                             final PublicKey receivingTokenProgramKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final PublicKey ixSysvarKey,
                                             final PublicKey receivingMintKey,
                                             final RemoveCollateralParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(receivingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = REMOVE_COLLATERAL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RemoveCollateralIxData(Discriminator discriminator, RemoveCollateralParams params) implements Borsh {  

    public static RemoveCollateralIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static RemoveCollateralIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RemoveCollateralParams.read(_data, i);
      return new RemoveCollateralIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INCREASE_SIZE_DISCRIMINATOR = toDiscriminator(107, 13, 141, 238, 152, 165, 96, 87);

  public static Instruction increaseSize(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey positionKey,
                                         final PublicKey marketKey,
                                         final PublicKey targetCustodyKey,
                                         final PublicKey targetOracleAccountKey,
                                         final PublicKey collateralCustodyKey,
                                         final PublicKey collateralOracleAccountKey,
                                         final PublicKey collateralCustodyTokenAccountKey,
                                         final PublicKey collateralTokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey collateralMintKey,
                                         final IncreaseSizeParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(collateralTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(collateralMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = INCREASE_SIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record IncreaseSizeIxData(Discriminator discriminator, IncreaseSizeParams params) implements Borsh {  

    public static IncreaseSizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 29;

    public static IncreaseSizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = IncreaseSizeParams.read(_data, i);
      return new IncreaseSizeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator DECREASE_SIZE_DISCRIMINATOR = toDiscriminator(171, 28, 203, 29, 118, 16, 214, 169);

  public static Instruction decreaseSize(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey poolKey,
                                         final PublicKey positionKey,
                                         final PublicKey marketKey,
                                         final PublicKey targetCustodyKey,
                                         final PublicKey targetOracleAccountKey,
                                         final PublicKey collateralCustodyKey,
                                         final PublicKey collateralOracleAccountKey,
                                         final PublicKey collateralCustodyTokenAccountKey,
                                         final PublicKey collateralTokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey collateralMintKey,
                                         final DecreaseSizeParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(collateralTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(collateralMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = DECREASE_SIZE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record DecreaseSizeIxData(Discriminator discriminator, DecreaseSizeParams params) implements Borsh {  

    public static DecreaseSizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 29;

    public static DecreaseSizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = DecreaseSizeParams.read(_data, i);
      return new DecreaseSizeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CANCEL_ALL_TRIGGER_ORDERS_DISCRIMINATOR = toDiscriminator(130, 108, 33, 153, 228, 31, 216, 219);

  public static Instruction cancelAllTriggerOrders(final AccountMeta invokedPerpetualsProgramMeta,
                                                   final PublicKey positionKey,
                                                   final PublicKey orderKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey) {
    final var keys = List.of(
      createRead(positionKey),
      createWrite(orderKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, CANCEL_ALL_TRIGGER_ORDERS_DISCRIMINATOR);
  }

  public static final Discriminator CANCEL_TRIGGER_ORDER_DISCRIMINATOR = toDiscriminator(144, 84, 67, 39, 27, 25, 202, 141);

  public static Instruction cancelTriggerOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                               final PublicKey ownerKey,
                                               final PublicKey orderKey,
                                               final PublicKey eventAuthorityKey,
                                               final PublicKey programKey,
                                               final CancelTriggerOrderParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(orderKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = CANCEL_TRIGGER_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CancelTriggerOrderIxData(Discriminator discriminator, CancelTriggerOrderParams params) implements Borsh {  

    public static CancelTriggerOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static CancelTriggerOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CancelTriggerOrderParams.read(_data, i);
      return new CancelTriggerOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CANCEL_UNSTAKE_TOKEN_REQUEST_DISCRIMINATOR = toDiscriminator(145, 133, 31, 216, 203, 198, 96, 8);

  public static Instruction cancelUnstakeTokenRequest(final AccountMeta invokedPerpetualsProgramMeta,
                                                      final PublicKey ownerKey,
                                                      final PublicKey tokenVaultKey,
                                                      final PublicKey tokenStakeAccountKey,
                                                      final PublicKey eventAuthorityKey,
                                                      final PublicKey programKey,
                                                      final CancelUnstakeTokenRequestParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(tokenVaultKey),
      createWrite(tokenStakeAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = CANCEL_UNSTAKE_TOKEN_REQUEST_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record CancelUnstakeTokenRequestIxData(Discriminator discriminator, CancelUnstakeTokenRequestParams params) implements Borsh {  

    public static CancelUnstakeTokenRequestIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static CancelUnstakeTokenRequestIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = CancelUnstakeTokenRequestParams.read(_data, i);
      return new CancelUnstakeTokenRequestIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLOSE_POSITION_DISCRIMINATOR = toDiscriminator(123, 134, 81, 0, 49, 68, 98, 98);

  public static Instruction closePosition(final AccountMeta invokedPerpetualsProgramMeta,
                                          final PublicKey ownerKey,
                                          final PublicKey feePayerKey,
                                          final PublicKey receivingAccountKey,
                                          final PublicKey transferAuthorityKey,
                                          final PublicKey perpetualsKey,
                                          final PublicKey poolKey,
                                          final PublicKey positionKey,
                                          final PublicKey marketKey,
                                          final PublicKey targetCustodyKey,
                                          final PublicKey targetOracleAccountKey,
                                          final PublicKey collateralCustodyKey,
                                          final PublicKey collateralOracleAccountKey,
                                          final PublicKey collateralCustodyTokenAccountKey,
                                          final PublicKey collateralTokenProgramKey,
                                          final PublicKey eventAuthorityKey,
                                          final PublicKey programKey,
                                          final PublicKey ixSysvarKey,
                                          final PublicKey collateralMintKey,
                                          final ClosePositionParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(collateralTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(collateralMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = CLOSE_POSITION_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ClosePositionIxData(Discriminator discriminator, ClosePositionParams params) implements Borsh {  

    public static ClosePositionIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 21;

    public static ClosePositionIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ClosePositionParams.read(_data, i);
      return new ClosePositionIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTE_LIMIT_WITH_SWAP_DISCRIMINATOR = toDiscriminator(253, 77, 100, 122, 194, 179, 54, 45);

  public static Instruction executeLimitWithSwap(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey positionOwnerKey,
                                                 final PublicKey feePayerKey,
                                                 final PublicKey transferAuthorityKey,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey reserveCustodyKey,
                                                 final PublicKey reserveCustodyOracleAccountKey,
                                                 final PublicKey positionKey,
                                                 final PublicKey orderKey,
                                                 final PublicKey marketKey,
                                                 final PublicKey targetCustodyKey,
                                                 final PublicKey targetOracleAccountKey,
                                                 final PublicKey collateralCustodyKey,
                                                 final PublicKey collateralOracleAccountKey,
                                                 final PublicKey collateralCustodyTokenAccountKey,
                                                 final PublicKey systemProgramKey,
                                                 final PublicKey collateralTokenProgramKey,
                                                 final PublicKey eventAuthorityKey,
                                                 final PublicKey programKey,
                                                 final PublicKey ixSysvarKey,
                                                 final PublicKey collateralMintKey,
                                                 final ExecuteLimitWithSwapParams params) {
    final var keys = List.of(
      createWrite(positionOwnerKey),
      createWrite(feePayerKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(reserveCustodyKey),
      createRead(reserveCustodyOracleAccountKey),
      createWrite(positionKey),
      createWrite(orderKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(collateralTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(collateralMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EXECUTE_LIMIT_WITH_SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ExecuteLimitWithSwapIxData(Discriminator discriminator, ExecuteLimitWithSwapParams params) implements Borsh {  

    public static ExecuteLimitWithSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static ExecuteLimitWithSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ExecuteLimitWithSwapParams.read(_data, i);
      return new ExecuteLimitWithSwapIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTE_LIMIT_ORDER_DISCRIMINATOR = toDiscriminator(52, 33, 60, 30, 47, 100, 40, 22);

  public static Instruction executeLimitOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey positionOwnerKey,
                                              final PublicKey feePayerKey,
                                              final PublicKey transferAuthorityKey,
                                              final PublicKey perpetualsKey,
                                              final PublicKey poolKey,
                                              final PublicKey positionKey,
                                              final PublicKey orderKey,
                                              final PublicKey marketKey,
                                              final PublicKey targetCustodyKey,
                                              final PublicKey targetOracleAccountKey,
                                              final PublicKey collateralCustodyKey,
                                              final PublicKey collateralOracleAccountKey,
                                              final PublicKey collateralCustodyTokenAccountKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey collateralTokenProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final PublicKey ixSysvarKey,
                                              final PublicKey collateralMintKey,
                                              final ExecuteLimitOrderParams params) {
    final var keys = List.of(
      createWrite(positionOwnerKey),
      createWritableSigner(feePayerKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(orderKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(systemProgramKey),
      createRead(collateralTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(collateralMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EXECUTE_LIMIT_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ExecuteLimitOrderIxData(Discriminator discriminator, ExecuteLimitOrderParams params) implements Borsh {  

    public static ExecuteLimitOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static ExecuteLimitOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ExecuteLimitOrderParams.read(_data, i);
      return new ExecuteLimitOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PLACE_LIMIT_ORDER_DISCRIMINATOR = toDiscriminator(108, 176, 33, 186, 146, 229, 1, 197);

  public static Instruction placeLimitOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey ownerKey,
                                            final PublicKey feePayerKey,
                                            final PublicKey fundingAccountKey,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey positionKey,
                                            final PublicKey orderKey,
                                            final PublicKey marketKey,
                                            final PublicKey targetCustodyKey,
                                            final PublicKey targetOracleAccountKey,
                                            final PublicKey reserveCustodyKey,
                                            final PublicKey reserveOracleAccountKey,
                                            final PublicKey reserveCustodyTokenAccountKey,
                                            final PublicKey receiveCustodyKey,
                                            final PublicKey systemProgramKey,
                                            final PublicKey fundingTokenProgramKey,
                                            final PublicKey eventAuthorityKey,
                                            final PublicKey programKey,
                                            final PublicKey ixSysvarKey,
                                            final PublicKey fundingMintKey,
                                            final PlaceLimitOrderParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(fundingAccountKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(orderKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(reserveCustodyKey),
      createRead(reserveOracleAccountKey),
      createWrite(reserveCustodyTokenAccountKey),
      createRead(receiveCustodyKey),
      createRead(systemProgramKey),
      createRead(fundingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(fundingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PLACE_LIMIT_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record PlaceLimitOrderIxData(Discriminator discriminator, PlaceLimitOrderParams params) implements Borsh {  

    public static PlaceLimitOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 60;

    public static PlaceLimitOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PlaceLimitOrderParams.read(_data, i);
      return new PlaceLimitOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EDIT_LIMIT_ORDER_DISCRIMINATOR = toDiscriminator(42, 114, 3, 11, 137, 245, 206, 50);

  public static Instruction editLimitOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey ownerKey,
                                           final PublicKey feePayerKey,
                                           final PublicKey receivingAccountKey,
                                           final PublicKey transferAuthorityKey,
                                           final PublicKey perpetualsKey,
                                           final PublicKey poolKey,
                                           final PublicKey positionKey,
                                           final PublicKey orderKey,
                                           final PublicKey marketKey,
                                           final PublicKey targetCustodyKey,
                                           final PublicKey targetOracleAccountKey,
                                           final PublicKey reserveCustodyKey,
                                           final PublicKey reserveOracleAccountKey,
                                           final PublicKey reserveCustodyTokenAccountKey,
                                           final PublicKey receiveCustodyKey,
                                           final PublicKey receivingTokenProgramKey,
                                           final PublicKey eventAuthorityKey,
                                           final PublicKey programKey,
                                           final PublicKey ixSysvarKey,
                                           final PublicKey receivingMintKey,
                                           final EditLimitOrderParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createWrite(orderKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(reserveCustodyKey),
      createRead(reserveOracleAccountKey),
      createWrite(reserveCustodyTokenAccountKey),
      createRead(receiveCustodyKey),
      createRead(receivingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EDIT_LIMIT_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record EditLimitOrderIxData(Discriminator discriminator, EditLimitOrderParams params) implements Borsh {  

    public static EditLimitOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 53;

    public static EditLimitOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = EditLimitOrderParams.read(_data, i);
      return new EditLimitOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EDIT_TRIGGER_ORDER_DISCRIMINATOR = toDiscriminator(180, 43, 215, 112, 254, 116, 20, 133);

  public static Instruction editTriggerOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey ownerKey,
                                             final PublicKey perpetualsKey,
                                             final PublicKey poolKey,
                                             final PublicKey positionKey,
                                             final PublicKey orderKey,
                                             final PublicKey marketKey,
                                             final PublicKey targetCustodyKey,
                                             final PublicKey targetOracleAccountKey,
                                             final PublicKey collateralCustodyKey,
                                             final PublicKey collateralOracleAccountKey,
                                             final PublicKey receiveCustodyKey,
                                             final PublicKey eventAuthorityKey,
                                             final PublicKey programKey,
                                             final PublicKey ixSysvarKey,
                                             final EditTriggerOrderParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createWrite(orderKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(receiveCustodyKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EDIT_TRIGGER_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record EditTriggerOrderIxData(Discriminator discriminator, EditTriggerOrderParams params) implements Borsh {  

    public static EditTriggerOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 30;

    public static EditTriggerOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = EditTriggerOrderParams.read(_data, i);
      return new EditTriggerOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PLACE_TRIGGER_ORDER_DISCRIMINATOR = toDiscriminator(32, 156, 50, 188, 232, 159, 112, 236);

  public static Instruction placeTriggerOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                              final PublicKey ownerKey,
                                              final PublicKey feePayerKey,
                                              final PublicKey perpetualsKey,
                                              final PublicKey poolKey,
                                              final PublicKey positionKey,
                                              final PublicKey orderKey,
                                              final PublicKey marketKey,
                                              final PublicKey targetCustodyKey,
                                              final PublicKey targetOracleAccountKey,
                                              final PublicKey collateralCustodyKey,
                                              final PublicKey collateralOracleAccountKey,
                                              final PublicKey receiveCustodyKey,
                                              final PublicKey systemProgramKey,
                                              final PublicKey eventAuthorityKey,
                                              final PublicKey programKey,
                                              final PublicKey ixSysvarKey,
                                              final PlaceTriggerOrderParams params) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWritableSigner(feePayerKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createWrite(orderKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(receiveCustodyKey),
      createRead(systemProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = PLACE_TRIGGER_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record PlaceTriggerOrderIxData(Discriminator discriminator, PlaceTriggerOrderParams params) implements Borsh {  

    public static PlaceTriggerOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 29;

    public static PlaceTriggerOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = PlaceTriggerOrderParams.read(_data, i);
      return new PlaceTriggerOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTE_TRIGGER_WITH_SWAP_DISCRIMINATOR = toDiscriminator(198, 68, 87, 43, 203, 171, 123, 82);

  public static Instruction executeTriggerWithSwap(final AccountMeta invokedPerpetualsProgramMeta,
                                                   final PublicKey positionOwnerKey,
                                                   final PublicKey feePayerKey,
                                                   final PublicKey receivingAccountKey,
                                                   final PublicKey collateralAccountKey,
                                                   final PublicKey transferAuthorityKey,
                                                   final PublicKey perpetualsKey,
                                                   final PublicKey poolKey,
                                                   final PublicKey positionKey,
                                                   final PublicKey orderKey,
                                                   final PublicKey marketKey,
                                                   final PublicKey targetCustodyKey,
                                                   final PublicKey targetOracleAccountKey,
                                                   final PublicKey collateralCustodyKey,
                                                   final PublicKey collateralOracleAccountKey,
                                                   final PublicKey collateralCustodyTokenAccountKey,
                                                   final PublicKey dispensingCustodyKey,
                                                   final PublicKey dispensingOracleAccountKey,
                                                   final PublicKey dispensingCustodyTokenAccountKey,
                                                   final PublicKey eventAuthorityKey,
                                                   final PublicKey programKey,
                                                   final PublicKey ixSysvarKey,
                                                   final PublicKey receivingMintKey,
                                                   final PublicKey receivingTokenProgramKey,
                                                   final PublicKey collateralMintKey,
                                                   final PublicKey collateralTokenProgramKey,
                                                   final ExecuteTriggerWithSwapParams params) {
    final var keys = List.of(
      createWrite(positionOwnerKey),
      createWrite(feePayerKey),
      createWrite(receivingAccountKey),
      createWrite(collateralAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createWrite(positionKey),
      createWrite(orderKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createWrite(dispensingCustodyKey),
      createRead(dispensingOracleAccountKey),
      createWrite(dispensingCustodyTokenAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey),
      createRead(receivingTokenProgramKey),
      createRead(collateralMintKey),
      createRead(collateralTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EXECUTE_TRIGGER_WITH_SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ExecuteTriggerWithSwapIxData(Discriminator discriminator, ExecuteTriggerWithSwapParams params) implements Borsh {  

    public static ExecuteTriggerWithSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static ExecuteTriggerWithSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ExecuteTriggerWithSwapParams.read(_data, i);
      return new ExecuteTriggerWithSwapIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXECUTE_TRIGGER_ORDER_DISCRIMINATOR = toDiscriminator(105, 10, 104, 136, 215, 134, 84, 171);

  public static Instruction executeTriggerOrder(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey positionOwnerKey,
                                                final PublicKey feePayerKey,
                                                final PublicKey receivingAccountKey,
                                                final PublicKey transferAuthorityKey,
                                                final PublicKey perpetualsKey,
                                                final PublicKey poolKey,
                                                final PublicKey positionKey,
                                                final PublicKey orderKey,
                                                final PublicKey marketKey,
                                                final PublicKey targetCustodyKey,
                                                final PublicKey targetOracleAccountKey,
                                                final PublicKey collateralCustodyKey,
                                                final PublicKey collateralOracleAccountKey,
                                                final PublicKey collateralCustodyTokenAccountKey,
                                                final PublicKey receivingTokenProgramKey,
                                                final PublicKey eventAuthorityKey,
                                                final PublicKey programKey,
                                                final PublicKey ixSysvarKey,
                                                final PublicKey receivingMintKey,
                                                final ExecuteTriggerOrderParams params) {
    final var keys = List.of(
      createWrite(positionOwnerKey),
      createWrite(feePayerKey),
      createWrite(receivingAccountKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(orderKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createWrite(collateralCustodyTokenAccountKey),
      createRead(receivingTokenProgramKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey),
      createRead(receivingMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = EXECUTE_TRIGGER_ORDER_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record ExecuteTriggerOrderIxData(Discriminator discriminator, ExecuteTriggerOrderParams params) implements Borsh {  

    public static ExecuteTriggerOrderIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 11;

    public static ExecuteTriggerOrderIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = ExecuteTriggerOrderParams.read(_data, i);
      return new ExecuteTriggerOrderIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LEVEL_UP_DISCRIMINATOR = toDiscriminator(128, 64, 197, 116, 226, 129, 119, 234);

  public static Instruction levelUp(final AccountMeta invokedPerpetualsProgramMeta,
                                    final PublicKey ownerKey,
                                    final PublicKey perpetualsKey,
                                    final PublicKey poolKey,
                                    final PublicKey metadataAccountKey,
                                    final PublicKey tradingAccountKey,
                                    final PublicKey transferAuthorityKey,
                                    final PublicKey metadataProgramKey,
                                    final PublicKey nftMintKey,
                                    final PublicKey systemProgramKey,
                                    final PublicKey ixSysvarKey,
                                    final PublicKey authorizationRulesProgramKey,
                                    final PublicKey authorizationRulesAccountKey,
                                    final LevelUpParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(metadataAccountKey),
      createWrite(tradingAccountKey),
      createRead(transferAuthorityKey),
      createRead(metadataProgramKey),
      createWrite(nftMintKey),
      createRead(systemProgramKey),
      createRead(ixSysvarKey),
      createRead(authorizationRulesProgramKey),
      createRead(authorizationRulesAccountKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = LEVEL_UP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record LevelUpIxData(Discriminator discriminator, LevelUpParams params) implements Borsh {  

    public static LevelUpIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static LevelUpIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LevelUpParams.read(_data, i);
      return new LevelUpIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator LIQUIDATE_DISCRIMINATOR = toDiscriminator(223, 179, 226, 125, 48, 46, 39, 74);

  public static Instruction liquidate(final AccountMeta invokedPerpetualsProgramMeta,
                                      final PublicKey signerKey,
                                      final PublicKey perpetualsKey,
                                      final PublicKey poolKey,
                                      final PublicKey positionKey,
                                      final PublicKey marketKey,
                                      final PublicKey targetCustodyKey,
                                      final PublicKey targetOracleAccountKey,
                                      final PublicKey collateralCustodyKey,
                                      final PublicKey collateralOracleAccountKey,
                                      final PublicKey eventAuthorityKey,
                                      final PublicKey programKey,
                                      final PublicKey ixSysvarKey,
                                      final LiquidateParams params) {
    final var keys = List.of(
      createWritableSigner(signerKey),
      createRead(perpetualsKey),
      createRead(poolKey),
      createWrite(positionKey),
      createWrite(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createWrite(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = LIQUIDATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record LiquidateIxData(Discriminator discriminator, LiquidateParams params) implements Borsh {  

    public static LiquidateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static LiquidateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = LiquidateParams.read(_data, i);
      return new LiquidateIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_ADD_COMPOUNDING_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR = toDiscriminator(29, 1, 144, 58, 190, 228, 125, 229);

  public static Instruction getAddCompoundingLiquidityAmountAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                                                   final PublicKey perpetualsKey,
                                                                   final PublicKey poolKey,
                                                                   final PublicKey inCustodyKey,
                                                                   final PublicKey inCustodyOracleAccountKey,
                                                                   final PublicKey rewardCustodyKey,
                                                                   final PublicKey rewardCustodyOracleAccountKey,
                                                                   final PublicKey lpTokenMintKey,
                                                                   final PublicKey compoundingTokenMintKey,
                                                                   final PublicKey ixSysvarKey,
                                                                   final GetAddCompoundingLiquidityAmountAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(inCustodyKey),
      createRead(inCustodyOracleAccountKey),
      createRead(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createRead(lpTokenMintKey),
      createRead(compoundingTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ADD_COMPOUNDING_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetAddCompoundingLiquidityAmountAndFeeIxData(Discriminator discriminator, GetAddCompoundingLiquidityAmountAndFeeParams params) implements Borsh {  

    public static GetAddCompoundingLiquidityAmountAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static GetAddCompoundingLiquidityAmountAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetAddCompoundingLiquidityAmountAndFeeParams.read(_data, i);
      return new GetAddCompoundingLiquidityAmountAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_ADD_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR = toDiscriminator(172, 150, 249, 181, 233, 241, 78, 139);

  public static Instruction getAddLiquidityAmountAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                                        final PublicKey perpetualsKey,
                                                        final PublicKey poolKey,
                                                        final PublicKey custodyKey,
                                                        final PublicKey custodyOracleAccountKey,
                                                        final PublicKey lpTokenMintKey,
                                                        final PublicKey ixSysvarKey,
                                                        final GetAddLiquidityAmountAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(custodyKey),
      createRead(custodyOracleAccountKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ADD_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetAddLiquidityAmountAndFeeIxData(Discriminator discriminator, GetAddLiquidityAmountAndFeeParams params) implements Borsh {  

    public static GetAddLiquidityAmountAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static GetAddLiquidityAmountAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetAddLiquidityAmountAndFeeParams.read(_data, i);
      return new GetAddLiquidityAmountAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_REMOVE_COMPOUNDING_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR = toDiscriminator(90, 9, 144, 220, 42, 82, 41, 95);

  public static Instruction getRemoveCompoundingLiquidityAmountAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                                                      final PublicKey perpetualsKey,
                                                                      final PublicKey poolKey,
                                                                      final PublicKey outCustodyKey,
                                                                      final PublicKey outCustodyOracleAccountKey,
                                                                      final PublicKey rewardCustodyKey,
                                                                      final PublicKey rewardCustodyOracleAccountKey,
                                                                      final PublicKey lpTokenMintKey,
                                                                      final PublicKey compoundingTokenMintKey,
                                                                      final PublicKey ixSysvarKey,
                                                                      final GetRemoveCompoundingLiquidityAmountAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(outCustodyKey),
      createRead(outCustodyOracleAccountKey),
      createRead(rewardCustodyKey),
      createRead(rewardCustodyOracleAccountKey),
      createRead(lpTokenMintKey),
      createRead(compoundingTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_REMOVE_COMPOUNDING_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetRemoveCompoundingLiquidityAmountAndFeeIxData(Discriminator discriminator, GetRemoveCompoundingLiquidityAmountAndFeeParams params) implements Borsh {  

    public static GetRemoveCompoundingLiquidityAmountAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static GetRemoveCompoundingLiquidityAmountAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetRemoveCompoundingLiquidityAmountAndFeeParams.read(_data, i);
      return new GetRemoveCompoundingLiquidityAmountAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_REMOVE_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR = toDiscriminator(194, 226, 233, 102, 14, 21, 196, 7);

  public static Instruction getRemoveLiquidityAmountAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                                           final PublicKey perpetualsKey,
                                                           final PublicKey poolKey,
                                                           final PublicKey custodyKey,
                                                           final PublicKey custodyOracleAccountKey,
                                                           final PublicKey lpTokenMintKey,
                                                           final PublicKey ixSysvarKey,
                                                           final GetRemoveLiquidityAmountAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(custodyKey),
      createRead(custodyOracleAccountKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_REMOVE_LIQUIDITY_AMOUNT_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetRemoveLiquidityAmountAndFeeIxData(Discriminator discriminator, GetRemoveLiquidityAmountAndFeeParams params) implements Borsh {  

    public static GetRemoveLiquidityAmountAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static GetRemoveLiquidityAmountAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetRemoveLiquidityAmountAndFeeParams.read(_data, i);
      return new GetRemoveLiquidityAmountAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_ENTRY_PRICE_AND_FEE_DISCRIMINATOR = toDiscriminator(134, 30, 231, 199, 83, 72, 27, 99);

  public static Instruction getEntryPriceAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey perpetualsKey,
                                                final PublicKey poolKey,
                                                final PublicKey marketKey,
                                                final PublicKey targetCustodyKey,
                                                final PublicKey targetOracleAccountKey,
                                                final PublicKey collateralCustodyKey,
                                                final PublicKey collateralOracleAccountKey,
                                                final PublicKey ixSysvarKey,
                                                final GetEntryPriceAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ENTRY_PRICE_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetEntryPriceAndFeeIxData(Discriminator discriminator, GetEntryPriceAndFeeParams params) implements Borsh {  

    public static GetEntryPriceAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static GetEntryPriceAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetEntryPriceAndFeeParams.read(_data, i);
      return new GetEntryPriceAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_EXIT_PRICE_AND_FEE_DISCRIMINATOR = toDiscriminator(73, 77, 94, 31, 8, 9, 92, 32);

  public static Instruction getExitPriceAndFee(final AccountMeta invokedPerpetualsProgramMeta,
                                               final PublicKey perpetualsKey,
                                               final PublicKey poolKey,
                                               final PublicKey positionKey,
                                               final PublicKey marketKey,
                                               final PublicKey targetCustodyKey,
                                               final PublicKey targetOracleAccountKey,
                                               final PublicKey collateralCustodyKey,
                                               final PublicKey collateralOracleAccountKey,
                                               final PublicKey ixSysvarKey,
                                               final GetExitPriceAndFeeParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_EXIT_PRICE_AND_FEE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetExitPriceAndFeeIxData(Discriminator discriminator, GetExitPriceAndFeeParams params) implements Borsh {  

    public static GetExitPriceAndFeeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetExitPriceAndFeeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetExitPriceAndFeeParams.read(_data, i);
      return new GetExitPriceAndFeeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_PNL_DISCRIMINATOR = toDiscriminator(106, 212, 3, 250, 195, 224, 64, 160);

  public static Instruction getPnl(final AccountMeta invokedPerpetualsProgramMeta,
                                   final PublicKey perpetualsKey,
                                   final PublicKey poolKey,
                                   final PublicKey positionKey,
                                   final PublicKey marketKey,
                                   final PublicKey targetCustodyKey,
                                   final PublicKey custodyOracleAccountKey,
                                   final PublicKey collateralCustodyKey,
                                   final PublicKey collateralOracleAccountKey,
                                   final PublicKey ixSysvarKey,
                                   final GetPnlParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(custodyOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_PNL_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetPnlIxData(Discriminator discriminator, GetPnlParams params) implements Borsh {  

    public static GetPnlIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetPnlIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetPnlParams.read(_data, i);
      return new GetPnlIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_POSITION_DATA_DISCRIMINATOR = toDiscriminator(58, 14, 217, 248, 114, 44, 212, 140);

  public static Instruction getPositionData(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey positionKey,
                                            final PublicKey marketKey,
                                            final PublicKey targetCustodyKey,
                                            final PublicKey custodyOracleAccountKey,
                                            final PublicKey collateralCustodyKey,
                                            final PublicKey collateralOracleAccountKey,
                                            final PublicKey ixSysvarKey,
                                            final GetPositionDataParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(custodyOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_POSITION_DATA_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetPositionDataIxData(Discriminator discriminator, GetPositionDataParams params) implements Borsh {  

    public static GetPositionDataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetPositionDataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetPositionDataParams.read(_data, i);
      return new GetPositionDataIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_LIQUIDATION_STATE_DISCRIMINATOR = toDiscriminator(127, 126, 199, 117, 90, 89, 29, 50);

  public static Instruction getLiquidationState(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey perpetualsKey,
                                                final PublicKey poolKey,
                                                final PublicKey positionKey,
                                                final PublicKey marketKey,
                                                final PublicKey targetCustodyKey,
                                                final PublicKey targetOracleAccountKey,
                                                final PublicKey collateralCustodyKey,
                                                final PublicKey collateralOracleAccountKey,
                                                final PublicKey ixSysvarKey,
                                                final GetLiquidationStateParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_LIQUIDATION_STATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetLiquidationStateIxData(Discriminator discriminator, GetLiquidationStateParams params) implements Borsh {  

    public static GetLiquidationStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetLiquidationStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetLiquidationStateParams.read(_data, i);
      return new GetLiquidationStateIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_LIQUIDATION_PRICE_DISCRIMINATOR = toDiscriminator(73, 174, 119, 65, 149, 5, 73, 239);

  public static Instruction getLiquidationPrice(final AccountMeta invokedPerpetualsProgramMeta,
                                                final PublicKey perpetualsKey,
                                                final PublicKey poolKey,
                                                final PublicKey positionKey,
                                                final PublicKey marketKey,
                                                final PublicKey targetCustodyKey,
                                                final PublicKey targetOracleAccountKey,
                                                final PublicKey collateralCustodyKey,
                                                final PublicKey collateralOracleAccountKey,
                                                final PublicKey ixSysvarKey,
                                                final GetLiquidationPriceParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_LIQUIDATION_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetLiquidationPriceIxData(Discriminator discriminator, GetLiquidationPriceParams params) implements Borsh {  

    public static GetLiquidationPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetLiquidationPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetLiquidationPriceParams.read(_data, i);
      return new GetLiquidationPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_ORACLE_PRICE_DISCRIMINATOR = toDiscriminator(200, 20, 0, 106, 56, 210, 230, 140);

  public static Instruction getOraclePrice(final AccountMeta invokedPerpetualsProgramMeta,
                                           final PublicKey perpetualsKey,
                                           final PublicKey poolKey,
                                           final PublicKey custodyKey,
                                           final PublicKey custodyOracleAccountKey,
                                           final PublicKey ixSysvarKey,
                                           final GetOraclePriceParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(custodyKey),
      createRead(custodyOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ORACLE_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetOraclePriceIxData(Discriminator discriminator, GetOraclePriceParams params) implements Borsh {  

    public static GetOraclePriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetOraclePriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetOraclePriceParams.read(_data, i);
      return new GetOraclePriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_SWAP_AMOUNT_AND_FEES_DISCRIMINATOR = toDiscriminator(247, 121, 40, 99, 35, 82, 100, 32);

  public static Instruction getSwapAmountAndFees(final AccountMeta invokedPerpetualsProgramMeta,
                                                 final PublicKey perpetualsKey,
                                                 final PublicKey poolKey,
                                                 final PublicKey receivingCustodyKey,
                                                 final PublicKey receivingCustodyOracleAccountKey,
                                                 final PublicKey dispensingCustodyKey,
                                                 final PublicKey dispensingCustodyOracleAccountKey,
                                                 final PublicKey ixSysvarKey,
                                                 final GetSwapAmountAndFeesParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createRead(dispensingCustodyKey),
      createRead(dispensingCustodyOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_SWAP_AMOUNT_AND_FEES_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetSwapAmountAndFeesIxData(Discriminator discriminator, GetSwapAmountAndFeesParams params) implements Borsh {  

    public static GetSwapAmountAndFeesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static GetSwapAmountAndFeesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetSwapAmountAndFeesParams.read(_data, i);
      return new GetSwapAmountAndFeesIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_ASSETS_UNDER_MANAGEMENT_DISCRIMINATOR = toDiscriminator(44, 3, 161, 69, 174, 75, 137, 162);

  public static Instruction getAssetsUnderManagement(final AccountMeta invokedPerpetualsProgramMeta,
                                                     final PublicKey perpetualsKey,
                                                     final PublicKey poolKey,
                                                     final PublicKey ixSysvarKey,
                                                     final GetAssetsUnderManagementParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ASSETS_UNDER_MANAGEMENT_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetAssetsUnderManagementIxData(Discriminator discriminator, GetAssetsUnderManagementParams params) implements Borsh {  

    public static GetAssetsUnderManagementIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetAssetsUnderManagementIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetAssetsUnderManagementParams.read(_data, i);
      return new GetAssetsUnderManagementIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_COMPOUNDING_TOKEN_DATA_DISCRIMINATOR = toDiscriminator(108, 158, 186, 227, 231, 199, 25, 110);

  public static Instruction getCompoundingTokenData(final AccountMeta invokedPerpetualsProgramMeta,
                                                    final PublicKey perpetualsKey,
                                                    final PublicKey poolKey,
                                                    final PublicKey lpTokenMintKey,
                                                    final PublicKey ixSysvarKey,
                                                    final GetCompoundingTokenDataParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_COMPOUNDING_TOKEN_DATA_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetCompoundingTokenDataIxData(Discriminator discriminator, GetCompoundingTokenDataParams params) implements Borsh {  

    public static GetCompoundingTokenDataIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetCompoundingTokenDataIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetCompoundingTokenDataParams.read(_data, i);
      return new GetCompoundingTokenDataIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_LP_TOKEN_PRICE_DISCRIMINATOR = toDiscriminator(71, 172, 21, 25, 176, 168, 60, 10);

  public static Instruction getLpTokenPrice(final AccountMeta invokedPerpetualsProgramMeta,
                                            final PublicKey perpetualsKey,
                                            final PublicKey poolKey,
                                            final PublicKey lpTokenMintKey,
                                            final PublicKey ixSysvarKey,
                                            final GetLpTokenPriceParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_LP_TOKEN_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetLpTokenPriceIxData(Discriminator discriminator, GetLpTokenPriceParams params) implements Borsh {  

    public static GetLpTokenPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetLpTokenPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetLpTokenPriceParams.read(_data, i);
      return new GetLpTokenPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_COMPOUNDING_TOKEN_PRICE_DISCRIMINATOR = toDiscriminator(129, 82, 182, 136, 95, 171, 44, 63);

  public static Instruction getCompoundingTokenPrice(final AccountMeta invokedPerpetualsProgramMeta,
                                                     final PublicKey perpetualsKey,
                                                     final PublicKey poolKey,
                                                     final PublicKey lpTokenMintKey,
                                                     final PublicKey ixSysvarKey,
                                                     final GetCompoundingTokenPriceParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(lpTokenMintKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_COMPOUNDING_TOKEN_PRICE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetCompoundingTokenPriceIxData(Discriminator discriminator, GetCompoundingTokenPriceParams params) implements Borsh {  

    public static GetCompoundingTokenPriceIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static GetCompoundingTokenPriceIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetCompoundingTokenPriceParams.read(_data, i);
      return new GetCompoundingTokenPriceIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GET_POSITION_QUOTE_DISCRIMINATOR = toDiscriminator(99, 207, 18, 150, 236, 108, 219, 147);

  public static Instruction getPositionQuote(final AccountMeta invokedPerpetualsProgramMeta,
                                             final PublicKey perpetualsKey,
                                             final PublicKey poolKey,
                                             final PublicKey marketKey,
                                             final PublicKey targetCustodyKey,
                                             final PublicKey targetOracleAccountKey,
                                             final PublicKey collateralCustodyKey,
                                             final PublicKey collateralOracleAccountKey,
                                             final PublicKey receivingCustodyKey,
                                             final PublicKey receivingCustodyOracleAccountKey,
                                             final PublicKey ixSysvarKey,
                                             final GetPositionQuoteParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(receivingCustodyKey),
      createRead(receivingCustodyOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_POSITION_QUOTE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetPositionQuoteIxData(Discriminator discriminator, GetPositionQuoteParams params) implements Borsh {  

    public static GetPositionQuoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static GetPositionQuoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetPositionQuoteParams.read(_data, i);
      return new GetPositionQuoteIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator GET_ADD_COLLATERAL_QUOTE_DISCRIMINATOR = toDiscriminator(103, 106, 18, 57, 44, 127, 202, 159);

  public static Instruction getAddCollateralQuote(final AccountMeta invokedPerpetualsProgramMeta,
                                                  final PublicKey perpetualsKey,
                                                  final PublicKey poolKey,
                                                  final PublicKey positionKey,
                                                  final PublicKey marketKey,
                                                  final PublicKey targetCustodyKey,
                                                  final PublicKey targetOracleAccountKey,
                                                  final PublicKey collateralCustodyKey,
                                                  final PublicKey collateralOracleAccountKey,
                                                  final PublicKey receivingCustodyKey,
                                                  final PublicKey receivingOracleAccountKey,
                                                  final PublicKey ixSysvarKey,
                                                  final GetAddCollateralQuoteParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(positionKey),
      createRead(marketKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(receivingCustodyKey),
      createRead(receivingOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_ADD_COLLATERAL_QUOTE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetAddCollateralQuoteIxData(Discriminator discriminator, GetAddCollateralQuoteParams params) implements Borsh {  

    public static GetAddCollateralQuoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static GetAddCollateralQuoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetAddCollateralQuoteParams.read(_data, i);
      return new GetAddCollateralQuoteIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator GET_CLOSE_POSITION_QUOTE_DISCRIMINATOR = toDiscriminator(94, 207, 132, 38, 227, 145, 139, 133);

  public static Instruction getClosePositionQuote(final AccountMeta invokedPerpetualsProgramMeta,
                                                  final PublicKey perpetualsKey,
                                                  final PublicKey poolKey,
                                                  final PublicKey marketKey,
                                                  final PublicKey positionKey,
                                                  final PublicKey targetCustodyKey,
                                                  final PublicKey targetOracleAccountKey,
                                                  final PublicKey collateralCustodyKey,
                                                  final PublicKey collateralOracleAccountKey,
                                                  final PublicKey ixSysvarKey,
                                                  final GetClosePositionQuoteParams params) {
    final var keys = List.of(
      createRead(perpetualsKey),
      createRead(poolKey),
      createRead(marketKey),
      createRead(positionKey),
      createRead(targetCustodyKey),
      createRead(targetOracleAccountKey),
      createRead(collateralCustodyKey),
      createRead(collateralOracleAccountKey),
      createRead(ixSysvarKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = GET_CLOSE_POSITION_QUOTE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record GetClosePositionQuoteIxData(Discriminator discriminator, GetClosePositionQuoteParams params) implements Borsh {  

    public static GetClosePositionQuoteIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static GetClosePositionQuoteIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = GetClosePositionQuoteParams.read(_data, i);
      return new GetClosePositionQuoteIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator RENAME_FLP_DISCRIMINATOR = toDiscriminator(175, 28, 157, 91, 44, 165, 11, 165);

  public static Instruction renameFlp(final AccountMeta invokedPerpetualsProgramMeta,
                                      final PublicKey adminKey,
                                      final PublicKey multisigKey,
                                      final PublicKey transferAuthorityKey,
                                      final PublicKey perpetualsKey,
                                      final PublicKey poolKey,
                                      final PublicKey lpTokenMintKey,
                                      final PublicKey lpMetadataAccountKey,
                                      final PublicKey systemProgramKey,
                                      final PublicKey tokenProgramKey,
                                      final PublicKey metadataProgramKey,
                                      final PublicKey rentKey,
                                      final RenameFlpParams params) {
    final var keys = List.of(
      createWritableSigner(adminKey),
      createWrite(multisigKey),
      createRead(transferAuthorityKey),
      createRead(perpetualsKey),
      createWrite(poolKey),
      createRead(lpTokenMintKey),
      createWrite(lpMetadataAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(metadataProgramKey),
      createRead(rentKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = RENAME_FLP_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record RenameFlpIxData(Discriminator discriminator, RenameFlpParams params) implements Borsh {  

    public static RenameFlpIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static RenameFlpIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = RenameFlpParams.read(_data, i);
      return new RenameFlpIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(params);
    }
  }

  public static final Discriminator BURN_AND_CLAIM_DISCRIMINATOR = toDiscriminator(9, 220, 63, 96, 139, 201, 253, 158);

  public static Instruction burnAndClaim(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey receivingTokenAccountKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey tokenVaultKey,
                                         final PublicKey tokenVaultTokenAccountKey,
                                         final PublicKey metadataAccountKey,
                                         final PublicKey collectionMetadataKey,
                                         final PublicKey editionKey,
                                         final PublicKey tokenRecordKey,
                                         final PublicKey tradingAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey metadataProgramKey,
                                         final PublicKey nftMintKey,
                                         final PublicKey nftTokenAccountKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final PublicKey receivingTokenMintKey,
                                         final BurnAndClaimParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(receivingTokenAccountKey),
      createRead(perpetualsKey),
      createWrite(tokenVaultKey),
      createWrite(tokenVaultTokenAccountKey),
      createWrite(metadataAccountKey),
      createWrite(collectionMetadataKey),
      createWrite(editionKey),
      createWrite(tokenRecordKey),
      createWrite(tradingAccountKey),
      createWrite(transferAuthorityKey),
      createRead(metadataProgramKey),
      createWrite(nftMintKey),
      createWrite(nftTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(ixSysvarKey),
      createRead(eventAuthorityKey),
      createRead(programKey),
      createRead(receivingTokenMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = BURN_AND_CLAIM_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record BurnAndClaimIxData(Discriminator discriminator, BurnAndClaimParams params) implements Borsh {  

    public static BurnAndClaimIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static BurnAndClaimIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = BurnAndClaimParams.read(_data, i);
      return new BurnAndClaimIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator BURN_AND_STAKE_DISCRIMINATOR = toDiscriminator(96, 188, 177, 75, 235, 215, 124, 3);

  public static Instruction burnAndStake(final AccountMeta invokedPerpetualsProgramMeta,
                                         final PublicKey ownerKey,
                                         final PublicKey feePayerKey,
                                         final PublicKey perpetualsKey,
                                         final PublicKey tokenVaultKey,
                                         final PublicKey tokenVaultTokenAccountKey,
                                         final PublicKey tokenStakeAccountKey,
                                         final PublicKey metadataAccountKey,
                                         final PublicKey collectionMetadataKey,
                                         final PublicKey editionKey,
                                         final PublicKey tokenRecordKey,
                                         final PublicKey tradingAccountKey,
                                         final PublicKey transferAuthorityKey,
                                         final PublicKey metadataProgramKey,
                                         final PublicKey nftMintKey,
                                         final PublicKey nftTokenAccountKey,
                                         final PublicKey systemProgramKey,
                                         final PublicKey tokenProgramKey,
                                         final PublicKey ixSysvarKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final BurnAndStakeParams params) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWritableSigner(feePayerKey),
      createRead(perpetualsKey),
      createWrite(tokenVaultKey),
      createRead(tokenVaultTokenAccountKey),
      createWrite(tokenStakeAccountKey),
      createWrite(metadataAccountKey),
      createWrite(collectionMetadataKey),
      createWrite(editionKey),
      createWrite(tokenRecordKey),
      createWrite(tradingAccountKey),
      createWrite(transferAuthorityKey),
      createRead(metadataProgramKey),
      createWrite(nftMintKey),
      createWrite(nftTokenAccountKey),
      createRead(systemProgramKey),
      createRead(tokenProgramKey),
      createRead(ixSysvarKey),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(params)];
    int i = BURN_AND_STAKE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(params, _data, i);

    return Instruction.createInstruction(invokedPerpetualsProgramMeta, keys, _data);
  }

  public record BurnAndStakeIxData(Discriminator discriminator, BurnAndStakeParams params) implements Borsh {  

    public static BurnAndStakeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 8;

    public static BurnAndStakeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = BurnAndStakeParams.read(_data, i);
      return new BurnAndStakeIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  private PerpetualsProgram() {
  }
}
