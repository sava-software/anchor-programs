package software.sava.anchor.programs.marginfi.v2.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public record MarginfiGroup(PublicKey _address,
                            Discriminator discriminator,
                            // Broadly able to modify anything, and can set/remove other admins at will.
                            PublicKey admin,
                            // Bitmask for group settings flags.
                            // * 0: `PROGRAM_FEES_ENABLED` If set, program-level fees are enabled.
                            // * 1: `ARENA_GROUP` If set, this is an arena group, which can only have two banks
                            // * Bits 1-63: Reserved for future use.
                            long groupFlags,
                            // Caches information from the global `FeeState` so the FeeState can be omitted on certain ixes
                            FeeStateCache feeStateCache,
                            int banks,
                            byte[] pad0,
                            // This admin can configure collateral ratios above (but not below) the collateral ratio of
                            // certain banks , e.g. allow SOL to count as 90% collateral when borrowing an LST instead of
                            // the default rate.
                            PublicKey emodeAdmin,
                            // Can modify the fields in `config.interest_rate_config` but nothing else, for every bank under
                            // this group
                            PublicKey delegateCurveAdmin,
                            // Can modify the `deposit_limit`, `borrow_limit`, `total_asset_value_init_limit` but nothing
                            // else, for every bank under this group
                            PublicKey delegateLimitAdmin,
                            // Can modify the emissions `flags`, `emissions_rate` and `emissions_mint`, but nothing else,
                            // for every bank under this group
                            PublicKey delegateEmissionsAdmin,
                            long[][] padding0,
                            long[][] padding1,
                            long padding4) implements Borsh {

  public static final int BYTES = 1064;
  public static final int PAD_0_LEN = 6;
  public static final int PADDING_0_LEN = 18;
  public static final int PADDING_1_LEN = 32;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final Discriminator DISCRIMINATOR = toDiscriminator(182, 23, 173, 240, 151, 206, 182, 67);
  public static final Filter DISCRIMINATOR_FILTER = Filter.createMemCompFilter(0, DISCRIMINATOR.data());

  public static final int ADMIN_OFFSET = 8;
  public static final int GROUP_FLAGS_OFFSET = 40;
  public static final int FEE_STATE_CACHE_OFFSET = 48;
  public static final int BANKS_OFFSET = 120;
  public static final int PAD_0_OFFSET = 122;
  public static final int EMODE_ADMIN_OFFSET = 128;
  public static final int DELEGATE_CURVE_ADMIN_OFFSET = 160;
  public static final int DELEGATE_LIMIT_ADMIN_OFFSET = 192;
  public static final int DELEGATE_EMISSIONS_ADMIN_OFFSET = 224;
  public static final int PADDING_0_OFFSET = 256;
  public static final int PADDING_1_OFFSET = 544;
  public static final int PADDING_4_OFFSET = 1056;

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createGroupFlagsFilter(final long groupFlags) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, groupFlags);
    return Filter.createMemCompFilter(GROUP_FLAGS_OFFSET, _data);
  }

  public static Filter createFeeStateCacheFilter(final FeeStateCache feeStateCache) {
    return Filter.createMemCompFilter(FEE_STATE_CACHE_OFFSET, feeStateCache.write());
  }

  public static Filter createBanksFilter(final int banks) {
    final byte[] _data = new byte[2];
    putInt16LE(_data, 0, banks);
    return Filter.createMemCompFilter(BANKS_OFFSET, _data);
  }

  public static Filter createEmodeAdminFilter(final PublicKey emodeAdmin) {
    return Filter.createMemCompFilter(EMODE_ADMIN_OFFSET, emodeAdmin);
  }

  public static Filter createDelegateCurveAdminFilter(final PublicKey delegateCurveAdmin) {
    return Filter.createMemCompFilter(DELEGATE_CURVE_ADMIN_OFFSET, delegateCurveAdmin);
  }

  public static Filter createDelegateLimitAdminFilter(final PublicKey delegateLimitAdmin) {
    return Filter.createMemCompFilter(DELEGATE_LIMIT_ADMIN_OFFSET, delegateLimitAdmin);
  }

  public static Filter createDelegateEmissionsAdminFilter(final PublicKey delegateEmissionsAdmin) {
    return Filter.createMemCompFilter(DELEGATE_EMISSIONS_ADMIN_OFFSET, delegateEmissionsAdmin);
  }

  public static Filter createPadding4Filter(final long padding4) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding4);
    return Filter.createMemCompFilter(PADDING_4_OFFSET, _data);
  }

  public static MarginfiGroup read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static MarginfiGroup read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static MarginfiGroup read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], MarginfiGroup> FACTORY = MarginfiGroup::read;

  public static MarginfiGroup read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var admin = readPubKey(_data, i);
    i += 32;
    final var groupFlags = getInt64LE(_data, i);
    i += 8;
    final var feeStateCache = FeeStateCache.read(_data, i);
    i += Borsh.len(feeStateCache);
    final var banks = getInt16LE(_data, i);
    i += 2;
    final var pad0 = new byte[6];
    i += Borsh.readArray(pad0, _data, i);
    final var emodeAdmin = readPubKey(_data, i);
    i += 32;
    final var delegateCurveAdmin = readPubKey(_data, i);
    i += 32;
    final var delegateLimitAdmin = readPubKey(_data, i);
    i += 32;
    final var delegateEmissionsAdmin = readPubKey(_data, i);
    i += 32;
    final var padding0 = new long[18][2];
    i += Borsh.readArray(padding0, _data, i);
    final var padding1 = new long[32][2];
    i += Borsh.readArray(padding1, _data, i);
    final var padding4 = getInt64LE(_data, i);
    return new MarginfiGroup(_address,
                             discriminator,
                             admin,
                             groupFlags,
                             feeStateCache,
                             banks,
                             pad0,
                             emodeAdmin,
                             delegateCurveAdmin,
                             delegateLimitAdmin,
                             delegateEmissionsAdmin,
                             padding0,
                             padding1,
                             padding4);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    admin.write(_data, i);
    i += 32;
    putInt64LE(_data, i, groupFlags);
    i += 8;
    i += Borsh.write(feeStateCache, _data, i);
    putInt16LE(_data, i, banks);
    i += 2;
    i += Borsh.writeArrayChecked(pad0, 6, _data, i);
    emodeAdmin.write(_data, i);
    i += 32;
    delegateCurveAdmin.write(_data, i);
    i += 32;
    delegateLimitAdmin.write(_data, i);
    i += 32;
    delegateEmissionsAdmin.write(_data, i);
    i += 32;
    i += Borsh.writeArrayChecked(padding0, 18, _data, i);
    i += Borsh.writeArrayChecked(padding1, 32, _data, i);
    putInt64LE(_data, i, padding4);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
