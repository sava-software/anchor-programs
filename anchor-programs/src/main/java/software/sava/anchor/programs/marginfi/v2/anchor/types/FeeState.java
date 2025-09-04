package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

// Unique per-program. The Program Owner uses this account to administrate fees collected by the protocol
public record FeeState(PublicKey _address,
                       Discriminator discriminator,
                       // The fee state's own key. A PDA derived from just `b"feestate"`
                       PublicKey key,
                       // Can modify fees
                       PublicKey globalFeeAdmin,
                       // The base wallet for all protocol fees. All SOL fees go to this wallet. All non-SOL fees go
                       // to the canonical ATA of this wallet for that asset.
                       PublicKey globalFeeWallet,
                       long placeholder0,
                       // Flat fee assessed when a new bank is initialized, in lamports.
                       // * In SOL, in native decimals.
                       int bankInitFlatSolFee,
                       int bumpSeed,
                       byte[] padding0,
                       byte[] padding1,
                       // Fee collected by the program owner from all groups
                       WrappedI80F48 programFeeFixed,
                       // Fee collected by the program owner from all groups
                       WrappedI80F48 programFeeRate,
                       byte[] reserved0,
                       byte[] reserved1) implements Borsh {

  public static final int BYTES = 264;
  public static final int PADDING_0_LEN = 4;
  public static final int PADDING_1_LEN = 15;
  public static final int RESERVED_0_LEN = 32;
  public static final int RESERVED_1_LEN = 64;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(63, 224, 16, 85, 193, 36, 235, 220);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int KEY_OFFSET = 8;
  public static final int GLOBAL_FEE_ADMIN_OFFSET = 40;
  public static final int GLOBAL_FEE_WALLET_OFFSET = 72;
  public static final int PLACEHOLDER_0_OFFSET = 104;
  public static final int BANK_INIT_FLAT_SOL_FEE_OFFSET = 112;
  public static final int BUMP_SEED_OFFSET = 116;
  public static final int PADDING_0_OFFSET = 117;
  public static final int PADDING_1_OFFSET = 121;
  public static final int PROGRAM_FEE_FIXED_OFFSET = 136;
  public static final int PROGRAM_FEE_RATE_OFFSET = 152;
  public static final int RESERVED_0_OFFSET = 168;
  public static final int RESERVED_1_OFFSET = 200;

  public static Filter createKeyFilter(final PublicKey key) {
    return Filter.createMemCompFilter(KEY_OFFSET, key);
  }

  public static Filter createGlobalFeeAdminFilter(final PublicKey globalFeeAdmin) {
    return Filter.createMemCompFilter(GLOBAL_FEE_ADMIN_OFFSET, globalFeeAdmin);
  }

  public static Filter createGlobalFeeWalletFilter(final PublicKey globalFeeWallet) {
    return Filter.createMemCompFilter(GLOBAL_FEE_WALLET_OFFSET, globalFeeWallet);
  }

  public static Filter createPlaceholder0Filter(final long placeholder0) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, placeholder0);
    return Filter.createMemCompFilter(PLACEHOLDER_0_OFFSET, _data);
  }

  public static Filter createBankInitFlatSolFeeFilter(final int bankInitFlatSolFee) {
    final byte[] _data = new byte[4];
    putInt32LE(_data, 0, bankInitFlatSolFee);
    return Filter.createMemCompFilter(BANK_INIT_FLAT_SOL_FEE_OFFSET, _data);
  }

  public static Filter createBumpSeedFilter(final int bumpSeed) {
    return Filter.createMemCompFilter(BUMP_SEED_OFFSET, new byte[]{(byte) bumpSeed});
  }

  public static Filter createProgramFeeFixedFilter(final WrappedI80F48 programFeeFixed) {
    return Filter.createMemCompFilter(PROGRAM_FEE_FIXED_OFFSET, programFeeFixed.write());
  }

  public static Filter createProgramFeeRateFilter(final WrappedI80F48 programFeeRate) {
    return Filter.createMemCompFilter(PROGRAM_FEE_RATE_OFFSET, programFeeRate.write());
  }

  public static FeeState read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static FeeState read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static FeeState read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], FeeState> FACTORY = FeeState::read;

  public static FeeState read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var key = readPubKey(_data, i);
    i += 32;
    final var globalFeeAdmin = readPubKey(_data, i);
    i += 32;
    final var globalFeeWallet = readPubKey(_data, i);
    i += 32;
    final var placeholder0 = getInt64LE(_data, i);
    i += 8;
    final var bankInitFlatSolFee = getInt32LE(_data, i);
    i += 4;
    final var bumpSeed = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[4];
    i += Borsh.readArray(padding0, _data, i);
    final var padding1 = new byte[15];
    i += Borsh.readArray(padding1, _data, i);
    final var programFeeFixed = WrappedI80F48.read(_data, i);
    i += Borsh.len(programFeeFixed);
    final var programFeeRate = WrappedI80F48.read(_data, i);
    i += Borsh.len(programFeeRate);
    final var reserved0 = new byte[32];
    i += Borsh.readArray(reserved0, _data, i);
    final var reserved1 = new byte[64];
    Borsh.readArray(reserved1, _data, i);
    return new FeeState(_address,
                        discriminator,
                        key,
                        globalFeeAdmin,
                        globalFeeWallet,
                        placeholder0,
                        bankInitFlatSolFee,
                        bumpSeed,
                        padding0,
                        padding1,
                        programFeeFixed,
                        programFeeRate,
                        reserved0,
                        reserved1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    key.write(_data, i);
    i += 32;
    globalFeeAdmin.write(_data, i);
    i += 32;
    globalFeeWallet.write(_data, i);
    i += 32;
    putInt64LE(_data, i, placeholder0);
    i += 8;
    putInt32LE(_data, i, bankInitFlatSolFee);
    i += 4;
    _data[i] = (byte) bumpSeed;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    i += Borsh.writeArray(padding1, _data, i);
    i += Borsh.write(programFeeFixed, _data, i);
    i += Borsh.write(programFeeRate, _data, i);
    i += Borsh.writeArray(reserved0, _data, i);
    i += Borsh.writeArray(reserved1, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
