package software.sava.anchor.programs.drift.merkle.distributor.anchor.types;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;
import software.sava.rpc.json.http.response.AccountInfo;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;

// Holds whether or not a claimant has claimed tokens.
public record ClaimStatus(PublicKey _address,
                          Discriminator discriminator,
                          // Authority that claimed the tokens.
                          PublicKey claimant,
                          // Locked amount
                          long lockedAmount,
                          // Locked amount withdrawn
                          long lockedAmountWithdrawn,
                          // Unlocked amount
                          long unlockedAmount,
                          // Unlocked amount claimed
                          long unlockedAmountClaimed,
                          // indicate that whether admin can close this account, for testing purpose
                          boolean closable,
                          // admin of merkle tree, store for for testing purpose
                          PublicKey distributor) implements Borsh {

  public static final int BYTES = 105;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int CLAIMANT_OFFSET = 8;
  public static final int LOCKED_AMOUNT_OFFSET = 40;
  public static final int LOCKED_AMOUNT_WITHDRAWN_OFFSET = 48;
  public static final int UNLOCKED_AMOUNT_OFFSET = 56;
  public static final int UNLOCKED_AMOUNT_CLAIMED_OFFSET = 64;
  public static final int CLOSABLE_OFFSET = 72;
  public static final int DISTRIBUTOR_OFFSET = 73;

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

  public static Filter createUnlockedAmountClaimedFilter(final long unlockedAmountClaimed) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, unlockedAmountClaimed);
    return Filter.createMemCompFilter(UNLOCKED_AMOUNT_CLAIMED_OFFSET, _data);
  }

  public static Filter createClosableFilter(final boolean closable) {
    return Filter.createMemCompFilter(CLOSABLE_OFFSET, new byte[]{(byte) (closable ? 1 : 0)});
  }

  public static Filter createDistributorFilter(final PublicKey distributor) {
    return Filter.createMemCompFilter(DISTRIBUTOR_OFFSET, distributor);
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
    final var discriminator = createAnchorDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var claimant = readPubKey(_data, i);
    i += 32;
    final var lockedAmount = getInt64LE(_data, i);
    i += 8;
    final var lockedAmountWithdrawn = getInt64LE(_data, i);
    i += 8;
    final var unlockedAmount = getInt64LE(_data, i);
    i += 8;
    final var unlockedAmountClaimed = getInt64LE(_data, i);
    i += 8;
    final var closable = _data[i] == 1;
    ++i;
    final var distributor = readPubKey(_data, i);
    return new ClaimStatus(_address,
                           discriminator,
                           claimant,
                           lockedAmount,
                           lockedAmountWithdrawn,
                           unlockedAmount,
                           unlockedAmountClaimed,
                           closable,
                           distributor);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    claimant.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lockedAmount);
    i += 8;
    putInt64LE(_data, i, lockedAmountWithdrawn);
    i += 8;
    putInt64LE(_data, i, unlockedAmount);
    i += 8;
    putInt64LE(_data, i, unlockedAmountClaimed);
    i += 8;
    _data[i] = (byte) (closable ? 1 : 0);
    ++i;
    distributor.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
