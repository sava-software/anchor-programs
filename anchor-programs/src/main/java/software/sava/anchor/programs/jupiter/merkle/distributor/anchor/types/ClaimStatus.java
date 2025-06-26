package software.sava.anchor.programs.jupiter.merkle.distributor.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Holds whether or not a claimant has claimed tokens.
public record ClaimStatus(PublicKey _address,
                          Discriminator discriminator,
                          // admin of merkle tree, store for for testing purpose
                          PublicKey admin,
                          // distributor
                          PublicKey distributor,
                          // Authority that claimed the tokens.
                          PublicKey claimant,
                          // Locked amount
                          long lockedAmount,
                          // Locked amount withdrawn
                          long lockedAmountWithdrawn,
                          // Unlocked amount
                          long unlockedAmount,
                          // Bonus amount
                          long bonusAmount,
                          // indicate that whether admin can close this account, for testing purpose
                          int closable,
                          // padding 0
                          byte[] padding0,
                          // padding 1
                          BigInteger padding1) implements Borsh {

  public static final int BYTES = 160;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int ADMIN_OFFSET = 8;
  public static final int DISTRIBUTOR_OFFSET = 40;
  public static final int CLAIMANT_OFFSET = 72;
  public static final int LOCKED_AMOUNT_OFFSET = 104;
  public static final int LOCKED_AMOUNT_WITHDRAWN_OFFSET = 112;
  public static final int UNLOCKED_AMOUNT_OFFSET = 120;
  public static final int BONUS_AMOUNT_OFFSET = 128;
  public static final int CLOSABLE_OFFSET = 136;
  public static final int PADDING0_OFFSET = 137;
  public static final int PADDING1_OFFSET = 144;

  public static Filter createAdminFilter(final PublicKey admin) {
    return Filter.createMemCompFilter(ADMIN_OFFSET, admin);
  }

  public static Filter createDistributorFilter(final PublicKey distributor) {
    return Filter.createMemCompFilter(DISTRIBUTOR_OFFSET, distributor);
  }

  public static Filter createClaimantFilter(final PublicKey claimant) {
    return Filter.createMemCompFilter(CLAIMANT_OFFSET, claimant);
  }

  public static Filter createLockedAmountFilter(final long lockedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedAmount);
    return Filter.createMemCompFilter(LOCKED_AMOUNT_OFFSET, _data);
  }

  public static Filter createLockedAmountWithdrawnFilter(final long lockedAmountWithdrawn) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, lockedAmountWithdrawn);
    return Filter.createMemCompFilter(LOCKED_AMOUNT_WITHDRAWN_OFFSET, _data);
  }

  public static Filter createUnlockedAmountFilter(final long unlockedAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unlockedAmount);
    return Filter.createMemCompFilter(UNLOCKED_AMOUNT_OFFSET, _data);
  }

  public static Filter createBonusAmountFilter(final long bonusAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, bonusAmount);
    return Filter.createMemCompFilter(BONUS_AMOUNT_OFFSET, _data);
  }

  public static Filter createClosableFilter(final int closable) {
    return Filter.createMemCompFilter(CLOSABLE_OFFSET, new byte[]{(byte) closable});
  }

  public static Filter createPadding1Filter(final BigInteger padding1) {
    final byte[] _data = new byte[16];
    putInt128LE(_data, 0, padding1);
    return Filter.createMemCompFilter(PADDING1_OFFSET, _data);
  }

  public static ClaimStatus read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static ClaimStatus read(final AccountInfo<byte[]> accountInfo) {
    return read(accountInfo.pubKey(), accountInfo.data(), 0);
  }

  public static ClaimStatus read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], ClaimStatus> FACTORY = ClaimStatus::read;

  public static ClaimStatus read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var admin = readPubKey(_data, i);
    i += 32;
    final var distributor = readPubKey(_data, i);
    i += 32;
    final var claimant = readPubKey(_data, i);
    i += 32;
    final var lockedAmount = getInt64LE(_data, i);
    i += 8;
    final var lockedAmountWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var unlockedAmount = getInt64LE(_data, i);
    i += 8;
    final var bonusAmount = getInt64LE(_data, i);
    i += 8;
    final var closable = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[7];
    i += Borsh.readArray(padding0, _data, i);
    final var padding1 = getInt128LE(_data, i);
    return new ClaimStatus(_address,
                           discriminator,
                           admin,
                           distributor,
                           claimant,
                           lockedAmount,
                           lockedAmountWithdrawn,
                           unlockedAmount,
                           bonusAmount,
                           closable,
                           padding0,
                           padding1);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    admin.write(_data, i);
    i += 32;
    distributor.write(_data, i);
    i += 32;
    claimant.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockedAmount);
    i += 8;
    putInt64LE(_data, i, lockedAmountWithdrawn);
    i += 8;
    putInt64LE(_data, i, unlockedAmount);
    i += 8;
    putInt64LE(_data, i, bonusAmount);
    i += 8;
    _data[i] = (byte) closable;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    putInt128LE(_data, i, padding1);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
