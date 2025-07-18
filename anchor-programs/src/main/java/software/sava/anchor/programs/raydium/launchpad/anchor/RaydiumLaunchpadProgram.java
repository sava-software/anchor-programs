package software.sava.anchor.programs.raydium.launchpad.anchor;

import java.util.List;

import software.sava.anchor.programs.raydium.launchpad.anchor.types.CurveParams;
import software.sava.anchor.programs.raydium.launchpad.anchor.types.MintParams;
import software.sava.anchor.programs.raydium.launchpad.anchor.types.PlatformConfigParam;
import software.sava.anchor.programs.raydium.launchpad.anchor.types.PlatformParams;
import software.sava.anchor.programs.raydium.launchpad.anchor.types.VestingParams;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.anchor.AnchorUtil.writeDiscriminator;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class RaydiumLaunchpadProgram {

  public static final Discriminator BUY_EXACT_IN_DISCRIMINATOR = toDiscriminator(250, 234, 13, 123, 213, 156, 19, 236);

  // Use the given amount of quote tokens to purchase base tokens.
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `amount_in` - Amount of quote token to purchase
  // * `minimum_amount_out` - Minimum amount of base token to receive (slippage protection)
  // * `share_fee_rate` - Fee rate for the share
  // 
  public static Instruction buyExactIn(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       // The user performing the swap operation
                                       // Must sign the transaction and pay for fees
                                       final PublicKey payerKey,
                                       // PDA that acts as the authority for pool vault operations
                                       // Generated using AUTH_SEED
                                       final PublicKey authorityKey,
                                       // Global configuration account containing protocol-wide settings
                                       // Used to read protocol fee rates and curve type
                                       final PublicKey globalConfigKey,
                                       // Platform configuration account containing platform-wide settings
                                       // Used to read platform fee rate
                                       final PublicKey platformConfigKey,
                                       // The pool state account where the swap will be performed
                                       // Contains current pool parameters and balances
                                       final PublicKey poolStateKey,
                                       // The user's token account for base tokens (tokens being bought)
                                       // Will receive the output tokens after the swap
                                       final PublicKey userBaseTokenKey,
                                       // The user's token account for quote tokens (tokens being sold)
                                       // Will be debited for the input amount
                                       final PublicKey userQuoteTokenKey,
                                       // The pool's vault for base tokens
                                       // Will be debited to send tokens to the user
                                       final PublicKey baseVaultKey,
                                       // The pool's vault for quote tokens
                                       // Will receive the input tokens from the user
                                       final PublicKey quoteVaultKey,
                                       // The mint of the base token
                                       // Used for transfer fee calculations if applicable
                                       final PublicKey baseTokenMintKey,
                                       // The mint of the quote token
                                       final PublicKey quoteTokenMintKey,
                                       // SPL Token program for base token transfers
                                       final PublicKey baseTokenProgramKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final long amountIn,
                                       final long minimumAmountOut,
                                       final long shareFeeRate) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createRead(authorityKey),
      createRead(globalConfigKey),
      createRead(platformConfigKey),
      createWrite(poolStateKey),
      createWrite(userBaseTokenKey),
      createWrite(userQuoteTokenKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createRead(baseTokenMintKey),
      createRead(quoteTokenMintKey),
      createRead(baseTokenProgramKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(BUY_EXACT_IN_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minimumAmountOut);
    i += 8;
    putInt64LE(_data, i, shareFeeRate);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record BuyExactInIxData(Discriminator discriminator,
                                 long amountIn,
                                 long minimumAmountOut,
                                 long shareFeeRate) implements Borsh {  

    public static BuyExactInIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static BuyExactInIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var minimumAmountOut = getInt64LE(_data, i);
      i += 8;
      final var shareFeeRate = getInt64LE(_data, i);
      return new BuyExactInIxData(discriminator, amountIn, minimumAmountOut, shareFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      putInt64LE(_data, i, minimumAmountOut);
      i += 8;
      putInt64LE(_data, i, shareFeeRate);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator BUY_EXACT_OUT_DISCRIMINATOR = toDiscriminator(24, 211, 116, 40, 105, 3, 153, 56);

  // Use quote tokens to purchase the given amount of base tokens.
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `amount_out` - Amount of base token to receive
  // * `maximum_amount_in` - Maximum amount of quote token to purchase (slippage protection)
  // * `share_fee_rate` - Fee rate for the share
  public static Instruction buyExactOut(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        // The user performing the swap operation
                                        // Must sign the transaction and pay for fees
                                        final PublicKey payerKey,
                                        // PDA that acts as the authority for pool vault operations
                                        // Generated using AUTH_SEED
                                        final PublicKey authorityKey,
                                        // Global configuration account containing protocol-wide settings
                                        // Used to read protocol fee rates and curve type
                                        final PublicKey globalConfigKey,
                                        // Platform configuration account containing platform-wide settings
                                        // Used to read platform fee rate
                                        final PublicKey platformConfigKey,
                                        // The pool state account where the swap will be performed
                                        // Contains current pool parameters and balances
                                        final PublicKey poolStateKey,
                                        // The user's token account for base tokens (tokens being bought)
                                        // Will receive the output tokens after the swap
                                        final PublicKey userBaseTokenKey,
                                        // The user's token account for quote tokens (tokens being sold)
                                        // Will be debited for the input amount
                                        final PublicKey userQuoteTokenKey,
                                        // The pool's vault for base tokens
                                        // Will be debited to send tokens to the user
                                        final PublicKey baseVaultKey,
                                        // The pool's vault for quote tokens
                                        // Will receive the input tokens from the user
                                        final PublicKey quoteVaultKey,
                                        // The mint of the base token
                                        // Used for transfer fee calculations if applicable
                                        final PublicKey baseTokenMintKey,
                                        // The mint of the quote token
                                        final PublicKey quoteTokenMintKey,
                                        // SPL Token program for base token transfers
                                        final PublicKey baseTokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final long amountOut,
                                        final long maximumAmountIn,
                                        final long shareFeeRate) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createRead(authorityKey),
      createRead(globalConfigKey),
      createRead(platformConfigKey),
      createWrite(poolStateKey),
      createWrite(userBaseTokenKey),
      createWrite(userQuoteTokenKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createRead(baseTokenMintKey),
      createRead(quoteTokenMintKey),
      createRead(baseTokenProgramKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(BUY_EXACT_OUT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, maximumAmountIn);
    i += 8;
    putInt64LE(_data, i, shareFeeRate);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record BuyExactOutIxData(Discriminator discriminator,
                                  long amountOut,
                                  long maximumAmountIn,
                                  long shareFeeRate) implements Borsh {  

    public static BuyExactOutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static BuyExactOutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountOut = getInt64LE(_data, i);
      i += 8;
      final var maximumAmountIn = getInt64LE(_data, i);
      i += 8;
      final var shareFeeRate = getInt64LE(_data, i);
      return new BuyExactOutIxData(discriminator, amountOut, maximumAmountIn, shareFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountOut);
      i += 8;
      putInt64LE(_data, i, maximumAmountIn);
      i += 8;
      putInt64LE(_data, i, shareFeeRate);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CLAIM_PLATFORM_FEE_DISCRIMINATOR = toDiscriminator(156, 39, 208, 135, 76, 237, 61, 72);

  // Claim platform fee
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // 
  public static Instruction claimPlatformFee(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             // Only the wallet stored in platform_config can collect platform fees
                                             final PublicKey platformFeeWalletKey,
                                             // PDA that acts as the authority for pool vault and mint operations
                                             // Generated using AUTH_SEED
                                             final PublicKey authorityKey,
                                             // Account that stores the pool's state and parameters
                                             // PDA generated using POOL_SEED and both token mints
                                             final PublicKey poolStateKey,
                                             // The platform config account
                                             final PublicKey platformConfigKey,
                                             final PublicKey quoteVaultKey,
                                             // The address that receives the collected quote token fees
                                             final PublicKey recipientTokenAccountKey,
                                             // The mint of quote token vault
                                             final PublicKey quoteMintKey) {
    final var keys = List.of(
      createWritableSigner(platformFeeWalletKey),
      createRead(authorityKey),
      createWrite(poolStateKey),
      createRead(platformConfigKey),
      createWrite(quoteVaultKey),
      createWrite(recipientTokenAccountKey),
      createRead(quoteMintKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, CLAIM_PLATFORM_FEE_DISCRIMINATOR);
  }

  public static final Discriminator CLAIM_VESTED_TOKEN_DISCRIMINATOR = toDiscriminator(49, 33, 104, 30, 189, 157, 79, 35);

  // Claim vested token
  // # Arguments
  public static Instruction claimVestedToken(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                             final SolanaAccounts solanaAccounts,
                                             // The beneficiary of the vesting account
                                             final PublicKey beneficiaryKey,
                                             // PDA that acts as the authority for pool vault and mint operations
                                             // Generated using AUTH_SEED
                                             final PublicKey authorityKey,
                                             // Account that stores the pool's state and parameters
                                             // PDA generated using POOL_SEED and both token mints
                                             final PublicKey poolStateKey,
                                             // The vesting record account
                                             final PublicKey vestingRecordKey,
                                             // The pool's vault for base tokens
                                             // Will be debited to send tokens to the user
                                             final PublicKey baseVaultKey,
                                             final PublicKey userBaseTokenKey,
                                             // The mint for the base token (token being sold)
                                             // Created in this instruction with specified decimals
                                             final PublicKey baseTokenMintKey) {
    final var keys = List.of(
      createWritableSigner(beneficiaryKey),
      createRead(authorityKey),
      createWrite(poolStateKey),
      createWrite(vestingRecordKey),
      createWrite(baseVaultKey),
      createWritableSigner(userBaseTokenKey),
      createRead(baseTokenMintKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram())
    );

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, CLAIM_VESTED_TOKEN_DISCRIMINATOR);
  }

  public static final Discriminator COLLECT_FEE_DISCRIMINATOR = toDiscriminator(60, 173, 247, 103, 4, 93, 130, 48);

  // Collects accumulated fees from the pool
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // 
  public static Instruction collectFee(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       // Only protocol_fee_owner saved in global_config can collect protocol fee now
                                       final PublicKey ownerKey,
                                       final PublicKey authorityKey,
                                       // Pool state stores accumulated protocol fee amount
                                       final PublicKey poolStateKey,
                                       // Global config account stores owner
                                       final PublicKey globalConfigKey,
                                       // The address that holds pool tokens for quote token
                                       final PublicKey quoteVaultKey,
                                       // The mint of quote token vault
                                       final PublicKey quoteMintKey,
                                       // The address that receives the collected quote token fees
                                       final PublicKey recipientTokenAccountKey) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(authorityKey),
      createWrite(poolStateKey),
      createRead(globalConfigKey),
      createWrite(quoteVaultKey),
      createRead(quoteMintKey),
      createWrite(recipientTokenAccountKey),
      createRead(solanaAccounts.tokenProgram())
    );

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, COLLECT_FEE_DISCRIMINATOR);
  }

  public static final Discriminator COLLECT_MIGRATE_FEE_DISCRIMINATOR = toDiscriminator(255, 186, 150, 223, 235, 118, 201, 186);

  // Collects  migrate fees from the pool
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // 
  public static Instruction collectMigrateFee(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                              final SolanaAccounts solanaAccounts,
                                              // Only migrate_fee_owner saved in global_config can collect migrate fee now
                                              final PublicKey ownerKey,
                                              final PublicKey authorityKey,
                                              // Pool state stores accumulated protocol fee amount
                                              final PublicKey poolStateKey,
                                              // Global config account stores owner
                                              final PublicKey globalConfigKey,
                                              // The address that holds pool tokens for quote token
                                              final PublicKey quoteVaultKey,
                                              // The mint of quote token vault
                                              final PublicKey quoteMintKey,
                                              // The address that receives the collected quote token fees
                                              final PublicKey recipientTokenAccountKey) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createRead(authorityKey),
      createWrite(poolStateKey),
      createRead(globalConfigKey),
      createWrite(quoteVaultKey),
      createRead(quoteMintKey),
      createWrite(recipientTokenAccountKey),
      createRead(solanaAccounts.tokenProgram())
    );

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, COLLECT_MIGRATE_FEE_DISCRIMINATOR);
  }

  public static final Discriminator CREATE_CONFIG_DISCRIMINATOR = toDiscriminator(201, 207, 243, 114, 75, 111, 47, 189);

  // Creates a new configuration
  // # Arguments
  // 
  // * `ctx` - The accounts needed by instruction
  // * `curve_type` - The type of bonding curve (0: ConstantProduct)
  // * `index` - The index of config, there may be multiple config with the same curve type.
  // * `trade_fee_rate` - Trade fee rate, must be less than RATE_DENOMINATOR_VALUE
  // 
  public static Instruction createConfig(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         // The protocol owner/admin account
                                         // Must match the predefined admin address
                                         // Has authority to create and modify protocol configurations
                                         final PublicKey ownerKey,
                                         // Global configuration account that stores protocol-wide settings
                                         // PDA generated using GLOBAL_CONFIG_SEED, quote token mint, and curve type
                                         // Stores fee rates and protocol parameters
                                         final PublicKey globalConfigKey,
                                         // The mint address of the quote token (token used for buying)
                                         // This will be the standard token used for all pools with this config
                                         final PublicKey quoteTokenMintKey,
                                         // Account that will receive protocol fees
                                         final PublicKey protocolFeeOwnerKey,
                                         // Account that will receive migrate fees
                                         final PublicKey migrateFeeOwnerKey,
                                         // The control wallet address for migrating to amm
                                         final PublicKey migrateToAmmWalletKey,
                                         // The control wallet address for migrating to cpswap
                                         final PublicKey migrateToCpswapWalletKey,
                                         final int curveType,
                                         final int index,
                                         final long migrateFee,
                                         final long tradeFeeRate) {
    final var keys = List.of(
      createWritableSigner(ownerKey),
      createWrite(globalConfigKey),
      createRead(quoteTokenMintKey),
      createRead(protocolFeeOwnerKey),
      createRead(migrateFeeOwnerKey),
      createRead(migrateToAmmWalletKey),
      createRead(migrateToCpswapWalletKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[27];
    int i = writeDiscriminator(CREATE_CONFIG_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) curveType;
    ++i;
    putInt16LE(_data, i, index);
    i += 2;
    putInt64LE(_data, i, migrateFee);
    i += 8;
    putInt64LE(_data, i, tradeFeeRate);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record CreateConfigIxData(Discriminator discriminator,
                                   int curveType,
                                   int index,
                                   long migrateFee,
                                   long tradeFeeRate) implements Borsh {  

    public static CreateConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 27;

    public static CreateConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var curveType = _data[i] & 0xFF;
      ++i;
      final var index = getInt16LE(_data, i);
      i += 2;
      final var migrateFee = getInt64LE(_data, i);
      i += 8;
      final var tradeFeeRate = getInt64LE(_data, i);
      return new CreateConfigIxData(discriminator,
                                    curveType,
                                    index,
                                    migrateFee,
                                    tradeFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) curveType;
      ++i;
      putInt16LE(_data, i, index);
      i += 2;
      putInt64LE(_data, i, migrateFee);
      i += 8;
      putInt64LE(_data, i, tradeFeeRate);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator CREATE_PLATFORM_CONFIG_DISCRIMINATOR = toDiscriminator(176, 90, 196, 175, 253, 113, 220, 20);

  // Create platform config account
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // # Fields
  // * `fee_rate` - Fee rate of the platform
  // * `name` - Name of the platform
  // * `web` - Website of the platform
  // * `img` - Image link of the platform
  // 
  public static Instruction createPlatformConfig(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 // The account paying for the initialization costs
                                                 final PublicKey platformAdminKey,
                                                 final PublicKey platformFeeWalletKey,
                                                 final PublicKey platformNftWalletKey,
                                                 // The platform config account
                                                 final PublicKey platformConfigKey,
                                                 final PlatformParams platformParams) {
    final var keys = List.of(
      createWritableSigner(platformAdminKey),
      createRead(platformFeeWalletKey),
      createRead(platformNftWalletKey),
      createWrite(platformConfigKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[8 + Borsh.len(platformParams)];
    int i = writeDiscriminator(CREATE_PLATFORM_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(platformParams, _data, i);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record CreatePlatformConfigIxData(Discriminator discriminator, PlatformParams platformParams) implements Borsh {  

    public static CreatePlatformConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CreatePlatformConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var platformParams = PlatformParams.read(_data, i);
      return new CreatePlatformConfigIxData(discriminator, platformParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(platformParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(platformParams);
    }
  }

  public static final Discriminator CREATE_VESTING_ACCOUNT_DISCRIMINATOR = toDiscriminator(129, 178, 2, 13, 217, 172, 230, 218);

  // Create vesting account
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `share` - The share amount of base token to be vested
  // 
  public static Instruction createVestingAccount(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                                 final SolanaAccounts solanaAccounts,
                                                 // The account paying for the initialization costs
                                                 // This can be any account with sufficient SOL to cover the transaction
                                                 final PublicKey creatorKey,
                                                 final PublicKey beneficiaryKey,
                                                 // The pool state account
                                                 final PublicKey poolStateKey,
                                                 // The vesting record account
                                                 final PublicKey vestingRecordKey,
                                                 final long shareAmount) {
    final var keys = List.of(
      createWritableSigner(creatorKey),
      createWrite(beneficiaryKey),
      createWrite(poolStateKey),
      createWrite(vestingRecordKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = writeDiscriminator(CREATE_VESTING_ACCOUNT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, shareAmount);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record CreateVestingAccountIxData(Discriminator discriminator, long shareAmount) implements Borsh {  

    public static CreateVestingAccountIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static CreateVestingAccountIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var shareAmount = getInt64LE(_data, i);
      return new CreateVestingAccountIxData(discriminator, shareAmount);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, shareAmount);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_DISCRIMINATOR = toDiscriminator(175, 175, 109, 31, 13, 152, 155, 237);

  // Initializes a new trading pool
  // # Arguments
  // 
  // * `ctx` - The context of accounts containing pool and token information
  // 
  public static Instruction initialize(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       // The account paying for the initialization costs
                                       // This can be any account with sufficient SOL to cover the transaction
                                       final PublicKey payerKey,
                                       final PublicKey creatorKey,
                                       // Global configuration account containing protocol-wide settings
                                       // Includes settings like quote token mint and fee parameters
                                       final PublicKey globalConfigKey,
                                       // Platform configuration account containing platform info
                                       // Includes settings like the fee_rate, name, web, img of the platform
                                       final PublicKey platformConfigKey,
                                       // PDA that acts as the authority for pool vault and mint operations
                                       // Generated using AUTH_SEED
                                       final PublicKey authorityKey,
                                       // Account that stores the pool's state and parameters
                                       // PDA generated using POOL_SEED and both token mints
                                       final PublicKey poolStateKey,
                                       // The mint for the base token (token being sold)
                                       // Created in this instruction with specified decimals
                                       final PublicKey baseMintKey,
                                       // The mint for the quote token (token used to buy)
                                       // Must match the quote_mint specified in global config
                                       final PublicKey quoteMintKey,
                                       // Token account that holds the pool's base tokens
                                       // PDA generated using POOL_VAULT_SEED
                                       final PublicKey baseVaultKey,
                                       // Token account that holds the pool's quote tokens
                                       // PDA generated using POOL_VAULT_SEED
                                       final PublicKey quoteVaultKey,
                                       // Account to store the base token's metadata
                                       // Created using Metaplex metadata program
                                       final PublicKey metadataAccountKey,
                                       // Metaplex Token Metadata program
                                       // Used to create metadata for the base token
                                       final PublicKey metadataProgramKey,
                                       final PublicKey eventAuthorityKey,
                                       final PublicKey programKey,
                                       final MintParams baseMintParam,
                                       final CurveParams curveParam,
                                       final VestingParams vestingParam) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(creatorKey),
      createRead(globalConfigKey),
      createRead(platformConfigKey),
      createRead(authorityKey),
      createWrite(poolStateKey),
      createWritableSigner(baseMintKey),
      createRead(quoteMintKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createWrite(metadataAccountKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(metadataProgramKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(baseMintParam) + Borsh.len(curveParam) + Borsh.len(vestingParam)];
    int i = writeDiscriminator(INITIALIZE_DISCRIMINATOR, _data, 0);
    i += Borsh.write(baseMintParam, _data, i);
    i += Borsh.write(curveParam, _data, i);
    Borsh.write(vestingParam, _data, i);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record InitializeIxData(Discriminator discriminator,
                                 MintParams baseMintParam,
                                 CurveParams curveParam,
                                 VestingParams vestingParam) implements Borsh {  

    public static InitializeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseMintParam = MintParams.read(_data, i);
      i += Borsh.len(baseMintParam);
      final var curveParam = CurveParams.read(_data, i);
      i += Borsh.len(curveParam);
      final var vestingParam = VestingParams.read(_data, i);
      return new InitializeIxData(discriminator, baseMintParam, curveParam, vestingParam);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(baseMintParam, _data, i);
      i += Borsh.write(curveParam, _data, i);
      i += Borsh.write(vestingParam, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(baseMintParam) + Borsh.len(curveParam) + Borsh.len(vestingParam);
    }
  }

  public static final Discriminator MIGRATE_TO_AMM_DISCRIMINATOR = toDiscriminator(207, 82, 192, 145, 254, 207, 145, 223);

  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // 
  public static Instruction migrateToAmm(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         // Only migrate_to_amm_wallet can migrate to cpswap pool
                                         // This signer must match the migrate_to_amm_wallet saved in global_config
                                         final PublicKey payerKey,
                                         // The mint for the base token (token being sold)
                                         final PublicKey baseMintKey,
                                         // The mint for the quote token (token used to buy)
                                         final PublicKey quoteMintKey,
                                         final PublicKey openbookProgramKey,
                                         // Account created and asigned to openbook_program but not been initialized
                                         final PublicKey marketKey,
                                         // Account created and asigned to openbook_program but not been initialized
                                         final PublicKey requestQueueKey,
                                         // Account created and asigned to openbook_program but not been initialized
                                         final PublicKey eventQueueKey,
                                         // Account created and asigned to openbook_program but not been initialized
                                         final PublicKey bidsKey,
                                         // Account created and asigned to openbook_program but not been initialized
                                         final PublicKey asksKey,
                                         final PublicKey marketVaultSignerKey,
                                         // Token account that holds the market's base tokens
                                         final PublicKey marketBaseVaultKey,
                                         // Token account that holds the market's quote tokens
                                         final PublicKey marketQuoteVaultKey,
                                         final PublicKey ammProgramKey,
                                         final PublicKey ammPoolKey,
                                         final PublicKey ammAuthorityKey,
                                         final PublicKey ammOpenOrdersKey,
                                         final PublicKey ammLpMintKey,
                                         final PublicKey ammBaseVaultKey,
                                         final PublicKey ammQuoteVaultKey,
                                         final PublicKey ammTargetOrdersKey,
                                         final PublicKey ammConfigKey,
                                         final PublicKey ammCreateFeeDestinationKey,
                                         // PDA that acts as the authority for pool vault operations
                                         // Generated using AUTH_SEED
                                         final PublicKey authorityKey,
                                         // Account that stores the pool's state and parameters
                                         // PDA generated using POOL_SEED and both token mints
                                         final PublicKey poolStateKey,
                                         // Global config account stores owner
                                         final PublicKey globalConfigKey,
                                         // The pool's vault for base tokens
                                         // Will be fully drained during migration
                                         final PublicKey baseVaultKey,
                                         // The pool's vault for quote tokens
                                         // Will be fully drained during migration
                                         final PublicKey quoteVaultKey,
                                         final PublicKey poolLpTokenKey,
                                         final long baseLotSize,
                                         final long quoteLotSize,
                                         final int marketVaultSignerNonce) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(baseMintKey),
      createRead(quoteMintKey),
      createRead(openbookProgramKey),
      createWrite(marketKey),
      createWrite(requestQueueKey),
      createWrite(eventQueueKey),
      createWrite(bidsKey),
      createWrite(asksKey),
      createRead(marketVaultSignerKey),
      createWrite(marketBaseVaultKey),
      createWrite(marketQuoteVaultKey),
      createRead(ammProgramKey),
      createWrite(ammPoolKey),
      createRead(ammAuthorityKey),
      createWrite(ammOpenOrdersKey),
      createWrite(ammLpMintKey),
      createWrite(ammBaseVaultKey),
      createWrite(ammQuoteVaultKey),
      createWrite(ammTargetOrdersKey),
      createRead(ammConfigKey),
      createWrite(ammCreateFeeDestinationKey),
      createWrite(authorityKey),
      createWrite(poolStateKey),
      createRead(globalConfigKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createWrite(poolLpTokenKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar())
    );

    final byte[] _data = new byte[25];
    int i = writeDiscriminator(MIGRATE_TO_AMM_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, baseLotSize);
    i += 8;
    putInt64LE(_data, i, quoteLotSize);
    i += 8;
    _data[i] = (byte) marketVaultSignerNonce;

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record MigrateToAmmIxData(Discriminator discriminator,
                                   long baseLotSize,
                                   long quoteLotSize,
                                   int marketVaultSignerNonce) implements Borsh {  

    public static MigrateToAmmIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 25;

    public static MigrateToAmmIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var baseLotSize = getInt64LE(_data, i);
      i += 8;
      final var quoteLotSize = getInt64LE(_data, i);
      i += 8;
      final var marketVaultSignerNonce = _data[i] & 0xFF;
      return new MigrateToAmmIxData(discriminator, baseLotSize, quoteLotSize, marketVaultSignerNonce);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, baseLotSize);
      i += 8;
      putInt64LE(_data, i, quoteLotSize);
      i += 8;
      _data[i] = (byte) marketVaultSignerNonce;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator MIGRATE_TO_CPSWAP_DISCRIMINATOR = toDiscriminator(136, 92, 200, 103, 28, 218, 144, 140);

  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // 
  public static Instruction migrateToCpswap(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            // Only migrate_to_cpswap_wallet can migrate to cpswap pool
                                            // This signer must match the migrate_to_cpswap_wallet saved in global_config
                                            final PublicKey payerKey,
                                            // The mint for the base token (token being sold)
                                            final PublicKey baseMintKey,
                                            // The mint for the quote token (token used to buy)
                                            final PublicKey quoteMintKey,
                                            // Platform configuration account containing platform-wide settings
                                            // Used to read platform fee rate
                                            final PublicKey platformConfigKey,
                                            final PublicKey cpswapProgramKey,
                                            // PDA account:
                                            // seeds = [
                                            // b"pool",
                                            // cpswap_config.key().as_ref(),
                                            // token_0_mint.key().as_ref(),
                                            // token_1_mint.key().as_ref(),
                                            // ],
                                            // seeds::program = cpswap_program,
                                            // 
                                            // Or random account: must be signed by cli
                                            final PublicKey cpswapPoolKey,
                                            final PublicKey cpswapAuthorityKey,
                                            final PublicKey cpswapLpMintKey,
                                            final PublicKey cpswapBaseVaultKey,
                                            final PublicKey cpswapQuoteVaultKey,
                                            final PublicKey cpswapConfigKey,
                                            final PublicKey cpswapCreatePoolFeeKey,
                                            final PublicKey cpswapObservationKey,
                                            final PublicKey lockProgramKey,
                                            final PublicKey lockAuthorityKey,
                                            final PublicKey lockLpVaultKey,
                                            // PDA that acts as the authority for pool vault operations
                                            // Generated using AUTH_SEED
                                            final PublicKey authorityKey,
                                            // Account that stores the pool's state and parameters
                                            // PDA generated using POOL_SEED and both token mints
                                            final PublicKey poolStateKey,
                                            // Global config account stores owner
                                            final PublicKey globalConfigKey,
                                            // The pool's vault for base tokens
                                            // Will be fully drained during migration
                                            final PublicKey baseVaultKey,
                                            // The pool's vault for quote tokens
                                            // Will be fully drained during migration
                                            final PublicKey quoteVaultKey,
                                            final PublicKey poolLpTokenKey,
                                            // Program to create NFT metadata accunt
                                            final PublicKey metadataProgramKey) {
    final var keys = List.of(
      createWritableSigner(payerKey),
      createRead(baseMintKey),
      createRead(quoteMintKey),
      createRead(platformConfigKey),
      createRead(cpswapProgramKey),
      createWrite(cpswapPoolKey),
      createRead(cpswapAuthorityKey),
      createWrite(cpswapLpMintKey),
      createWrite(cpswapBaseVaultKey),
      createWrite(cpswapQuoteVaultKey),
      createRead(cpswapConfigKey),
      createWrite(cpswapCreatePoolFeeKey),
      createWrite(cpswapObservationKey),
      createRead(lockProgramKey),
      createRead(lockAuthorityKey),
      createWrite(lockLpVaultKey),
      createWrite(authorityKey),
      createWrite(poolStateKey),
      createRead(globalConfigKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createWrite(poolLpTokenKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.tokenProgram()),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(solanaAccounts.systemProgram()),
      createRead(solanaAccounts.rentSysVar()),
      createRead(metadataProgramKey)
    );

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, MIGRATE_TO_CPSWAP_DISCRIMINATOR);
  }

  public static final Discriminator SELL_EXACT_IN_DISCRIMINATOR = toDiscriminator(149, 39, 222, 155, 211, 124, 152, 26);

  // Use the given amount of base tokens to sell for quote tokens.
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `amount_in` - Amount of base token to sell
  // * `minimum_amount_out` - Minimum amount of quote token to receive (slippage protection)
  // * `share_fee_rate` - Fee rate for the share
  // 
  public static Instruction sellExactIn(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        // The user performing the swap operation
                                        // Must sign the transaction and pay for fees
                                        final PublicKey payerKey,
                                        // PDA that acts as the authority for pool vault operations
                                        // Generated using AUTH_SEED
                                        final PublicKey authorityKey,
                                        // Global configuration account containing protocol-wide settings
                                        // Used to read protocol fee rates and curve type
                                        final PublicKey globalConfigKey,
                                        // Platform configuration account containing platform-wide settings
                                        // Used to read platform fee rate
                                        final PublicKey platformConfigKey,
                                        // The pool state account where the swap will be performed
                                        // Contains current pool parameters and balances
                                        final PublicKey poolStateKey,
                                        // The user's token account for base tokens (tokens being bought)
                                        // Will receive the output tokens after the swap
                                        final PublicKey userBaseTokenKey,
                                        // The user's token account for quote tokens (tokens being sold)
                                        // Will be debited for the input amount
                                        final PublicKey userQuoteTokenKey,
                                        // The pool's vault for base tokens
                                        // Will be debited to send tokens to the user
                                        final PublicKey baseVaultKey,
                                        // The pool's vault for quote tokens
                                        // Will receive the input tokens from the user
                                        final PublicKey quoteVaultKey,
                                        // The mint of the base token
                                        // Used for transfer fee calculations if applicable
                                        final PublicKey baseTokenMintKey,
                                        // The mint of the quote token
                                        final PublicKey quoteTokenMintKey,
                                        // SPL Token program for base token transfers
                                        final PublicKey baseTokenProgramKey,
                                        final PublicKey eventAuthorityKey,
                                        final PublicKey programKey,
                                        final long amountIn,
                                        final long minimumAmountOut,
                                        final long shareFeeRate) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createRead(authorityKey),
      createRead(globalConfigKey),
      createRead(platformConfigKey),
      createWrite(poolStateKey),
      createWrite(userBaseTokenKey),
      createWrite(userQuoteTokenKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createRead(baseTokenMintKey),
      createRead(quoteTokenMintKey),
      createRead(baseTokenProgramKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(SELL_EXACT_IN_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountIn);
    i += 8;
    putInt64LE(_data, i, minimumAmountOut);
    i += 8;
    putInt64LE(_data, i, shareFeeRate);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record SellExactInIxData(Discriminator discriminator,
                                  long amountIn,
                                  long minimumAmountOut,
                                  long shareFeeRate) implements Borsh {  

    public static SellExactInIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static SellExactInIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountIn = getInt64LE(_data, i);
      i += 8;
      final var minimumAmountOut = getInt64LE(_data, i);
      i += 8;
      final var shareFeeRate = getInt64LE(_data, i);
      return new SellExactInIxData(discriminator, amountIn, minimumAmountOut, shareFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountIn);
      i += 8;
      putInt64LE(_data, i, minimumAmountOut);
      i += 8;
      putInt64LE(_data, i, shareFeeRate);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SELL_EXACT_OUT_DISCRIMINATOR = toDiscriminator(95, 200, 71, 34, 8, 9, 11, 166);

  // Sell base tokens for the given amount of quote tokens.
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `amount_out` - Amount of quote token to receive
  // * `maximum_amount_in` - Maximum amount of base token to purchase (slippage protection)
  // * `share_fee_rate` - Fee rate for the share
  // 
  public static Instruction sellExactOut(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                         final SolanaAccounts solanaAccounts,
                                         // The user performing the swap operation
                                         // Must sign the transaction and pay for fees
                                         final PublicKey payerKey,
                                         // PDA that acts as the authority for pool vault operations
                                         // Generated using AUTH_SEED
                                         final PublicKey authorityKey,
                                         // Global configuration account containing protocol-wide settings
                                         // Used to read protocol fee rates and curve type
                                         final PublicKey globalConfigKey,
                                         // Platform configuration account containing platform-wide settings
                                         // Used to read platform fee rate
                                         final PublicKey platformConfigKey,
                                         // The pool state account where the swap will be performed
                                         // Contains current pool parameters and balances
                                         final PublicKey poolStateKey,
                                         // The user's token account for base tokens (tokens being bought)
                                         // Will receive the output tokens after the swap
                                         final PublicKey userBaseTokenKey,
                                         // The user's token account for quote tokens (tokens being sold)
                                         // Will be debited for the input amount
                                         final PublicKey userQuoteTokenKey,
                                         // The pool's vault for base tokens
                                         // Will be debited to send tokens to the user
                                         final PublicKey baseVaultKey,
                                         // The pool's vault for quote tokens
                                         // Will receive the input tokens from the user
                                         final PublicKey quoteVaultKey,
                                         // The mint of the base token
                                         // Used for transfer fee calculations if applicable
                                         final PublicKey baseTokenMintKey,
                                         // The mint of the quote token
                                         final PublicKey quoteTokenMintKey,
                                         // SPL Token program for base token transfers
                                         final PublicKey baseTokenProgramKey,
                                         final PublicKey eventAuthorityKey,
                                         final PublicKey programKey,
                                         final long amountOut,
                                         final long maximumAmountIn,
                                         final long shareFeeRate) {
    final var keys = List.of(
      createReadOnlySigner(payerKey),
      createRead(authorityKey),
      createRead(globalConfigKey),
      createRead(platformConfigKey),
      createWrite(poolStateKey),
      createWrite(userBaseTokenKey),
      createWrite(userQuoteTokenKey),
      createWrite(baseVaultKey),
      createWrite(quoteVaultKey),
      createRead(baseTokenMintKey),
      createRead(quoteTokenMintKey),
      createRead(baseTokenProgramKey),
      createRead(solanaAccounts.tokenProgram()),
      createRead(eventAuthorityKey),
      createRead(programKey)
    );

    final byte[] _data = new byte[32];
    int i = writeDiscriminator(SELL_EXACT_OUT_DISCRIMINATOR, _data, 0);
    putInt64LE(_data, i, amountOut);
    i += 8;
    putInt64LE(_data, i, maximumAmountIn);
    i += 8;
    putInt64LE(_data, i, shareFeeRate);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record SellExactOutIxData(Discriminator discriminator,
                                   long amountOut,
                                   long maximumAmountIn,
                                   long shareFeeRate) implements Borsh {  

    public static SellExactOutIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 32;

    public static SellExactOutIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amountOut = getInt64LE(_data, i);
      i += 8;
      final var maximumAmountIn = getInt64LE(_data, i);
      i += 8;
      final var shareFeeRate = getInt64LE(_data, i);
      return new SellExactOutIxData(discriminator, amountOut, maximumAmountIn, shareFeeRate);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amountOut);
      i += 8;
      putInt64LE(_data, i, maximumAmountIn);
      i += 8;
      putInt64LE(_data, i, shareFeeRate);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_CONFIG_DISCRIMINATOR = toDiscriminator(29, 158, 252, 191, 10, 83, 219, 99);

  // Updates configuration parameters
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `param` - Parameter to update:
  // - 0: Update trade_fee_rate
  // - 1: Update fee owner
  // * `value` - New value for the selected parameter
  // 
  public static Instruction updateConfig(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                         // The global config owner or admin
                                         final PublicKey ownerKey,
                                         // Global config account to be changed
                                         final PublicKey globalConfigKey,
                                         final int param,
                                         final long value) {
    final var keys = List.of(
      createReadOnlySigner(ownerKey),
      createWrite(globalConfigKey)
    );

    final byte[] _data = new byte[17];
    int i = writeDiscriminator(UPDATE_CONFIG_DISCRIMINATOR, _data, 0);
    _data[i] = (byte) param;
    ++i;
    putInt64LE(_data, i, value);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record UpdateConfigIxData(Discriminator discriminator, int param, long value) implements Borsh {  

    public static UpdateConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static UpdateConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var param = _data[i] & 0xFF;
      ++i;
      final var value = getInt64LE(_data, i);
      return new UpdateConfigIxData(discriminator, param, value);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) param;
      ++i;
      putInt64LE(_data, i, value);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_PLATFORM_CONFIG_DISCRIMINATOR = toDiscriminator(195, 60, 76, 129, 146, 45, 67, 143);

  // Update platform config
  // # Arguments
  // 
  // * `ctx` - The context of accounts
  // * `param` - Parameter to update
  // 
  public static Instruction updatePlatformConfig(final AccountMeta invokedRaydiumLaunchpadProgramMeta,
                                                 // The account paying for the initialization costs
                                                 final PublicKey platformAdminKey,
                                                 // Platform config account to be changed
                                                 final PublicKey platformConfigKey,
                                                 final PlatformConfigParam param) {
    final var keys = List.of(
      createReadOnlySigner(platformAdminKey),
      createWrite(platformConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(param)];
    int i = writeDiscriminator(UPDATE_PLATFORM_CONFIG_DISCRIMINATOR, _data, 0);
    Borsh.write(param, _data, i);

    return Instruction.createInstruction(invokedRaydiumLaunchpadProgramMeta, keys, _data);
  }

  public record UpdatePlatformConfigIxData(Discriminator discriminator, PlatformConfigParam param) implements Borsh {  

    public static UpdatePlatformConfigIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdatePlatformConfigIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = parseDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var param = PlatformConfigParam.read(_data, i);
      return new UpdatePlatformConfigIxData(discriminator, param);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(param, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(param);
    }
  }

  private RaydiumLaunchpadProgram() {
  }
}
