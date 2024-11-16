package software.sava.anchor.programs.jupiter.staking.anchor.types;

import java.math.BigInteger;

import java.util.function.BiFunction;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.rpc.Filter;

import static software.sava.anchor.AnchorUtil.parseDiscriminator;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Locks tokens on behalf of a user.
public record Escrow(PublicKey _address,
                     Discriminator discriminator,
                     // The [Locker] that this [Escrow] is part of.
                     PublicKey locker,
                     // The key of the account that is authorized to stake into/withdraw from this [Escrow].
                     PublicKey owner,
                     // Bump seed.
                     int bump,
                     // The token account holding the escrow tokens.
                     PublicKey tokens,
                     // Amount of tokens staked.
                     long amount,
                     // When the [Escrow::owner] started their escrow.
                     long escrowStartedAt,
                     // When the escrow unlocks; i.e. the [Escrow::owner] is scheduled to be allowed to withdraw their tokens.
                     long escrowEndsAt,
                     // Account that is authorized to vote on behalf of this [Escrow].
                     // Defaults to the [Escrow::owner].
                     PublicKey voteDelegate,
                     // Max lock
                     boolean isMaxLock,
                     // total amount of partial unstaking amount
                     long partialUnstakingAmount,
                     // padding for further use
                     long padding,
                     // buffer for further use
                     BigInteger[] buffers) implements Borsh {

  public static final int BYTES = 322;
  public static final Filter SIZE_FILTER = Filter.createDataSizeFilter(BYTES);

  public static final int LOCKER_OFFSET = 8;
  public static final int OWNER_OFFSET = 40;
  public static final int BUMP_OFFSET = 72;
  public static final int TOKENS_OFFSET = 73;
  public static final int AMOUNT_OFFSET = 105;
  public static final int ESCROW_STARTED_AT_OFFSET = 113;
  public static final int ESCROW_ENDS_AT_OFFSET = 121;
  public static final int VOTE_DELEGATE_OFFSET = 129;
  public static final int IS_MAX_LOCK_OFFSET = 161;
  public static final int PARTIAL_UNSTAKING_AMOUNT_OFFSET = 162;
  public static final int PADDING_OFFSET = 170;
  public static final int BUFFERS_OFFSET = 178;

  public static Filter createLockerFilter(final PublicKey locker) {
    return Filter.createMemCompFilter(LOCKER_OFFSET, locker);
  }

  public static Filter createOwnerFilter(final PublicKey owner) {
    return Filter.createMemCompFilter(OWNER_OFFSET, owner);
  }

  public static Filter createBumpFilter(final int bump) {
    return Filter.createMemCompFilter(BUMP_OFFSET, new byte[]{(byte) bump});
  }

  public static Filter createTokensFilter(final PublicKey tokens) {
    return Filter.createMemCompFilter(TOKENS_OFFSET, tokens);
  }

  public static Filter createAmountFilter(final long amount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, amount);
    return Filter.createMemCompFilter(AMOUNT_OFFSET, _data);
  }

  public static Filter createEscrowStartedAtFilter(final long escrowStartedAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, escrowStartedAt);
    return Filter.createMemCompFilter(ESCROW_STARTED_AT_OFFSET, _data);
  }

  public static Filter createEscrowEndsAtFilter(final long escrowEndsAt) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, escrowEndsAt);
    return Filter.createMemCompFilter(ESCROW_ENDS_AT_OFFSET, _data);
  }

  public static Filter createVoteDelegateFilter(final PublicKey voteDelegate) {
    return Filter.createMemCompFilter(VOTE_DELEGATE_OFFSET, voteDelegate);
  }

  public static Filter createIsMaxLockFilter(final boolean isMaxLock) {
    return Filter.createMemCompFilter(IS_MAX_LOCK_OFFSET, new byte[]{(byte) (isMaxLock ? 1 : 0)});
  }

  public static Filter createPartialUnstakingAmountFilter(final long partialUnstakingAmount) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, partialUnstakingAmount);
    return Filter.createMemCompFilter(PARTIAL_UNSTAKING_AMOUNT_OFFSET, _data);
  }

  public static Filter createPaddingFilter(final long padding) {
    final byte[] _data = new byte[8];
    putInt64LE(_data, 0, padding);
    return Filter.createMemCompFilter(PADDING_OFFSET, _data);
  }

  public static Escrow read(final byte[] _data, final int offset) {
    return read(null, _data, offset);
  }

  public static Escrow read(final PublicKey _address, final byte[] _data) {
    return read(_address, _data, 0);
  }

  public static final BiFunction<PublicKey, byte[], Escrow> FACTORY = Escrow::read;

  public static Escrow read(final PublicKey _address, final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var discriminator = parseDiscriminator(_data, offset);
    int i = offset + discriminator.length();
    final var locker = readPubKey(_data, i);
    i += 32;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var bump = _data[i] & 0xFF;
    ++i;
    final var tokens = readPubKey(_data, i);
    i += 32;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var escrowStartedAt = getInt64LE(_data, i);
    i += 8;
    final var escrowEndsAt = getInt64LE(_data, i);
    i += 8;
    final var voteDelegate = readPubKey(_data, i);
    i += 32;
    final var isMaxLock = _data[i] == 1;
    ++i;
    final var partialUnstakingAmount = getInt64LE(_data, i);
    i += 8;
    final var padding = getInt64LE(_data, i);
    i += 8;
    final var buffers = new BigInteger[9];
    Borsh.readArray(buffers, _data, i);
    return new Escrow(_address,
                      discriminator,
                      locker,
                      owner,
                      bump,
                      tokens,
                      amount,
                      escrowStartedAt,
                      escrowEndsAt,
                      voteDelegate,
                      isMaxLock,
                      partialUnstakingAmount,
                      padding,
                      buffers);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset + discriminator.write(_data, offset);
    locker.write(_data, i);
    i += 32;
    owner.write(_data, i);
    i += 32;
    _data[i] = (byte) bump;
    ++i;
    tokens.write(_data, i);
    i += 32;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, escrowStartedAt);
    i += 8;
    putInt64LE(_data, i, escrowEndsAt);
    i += 8;
    voteDelegate.write(_data, i);
    i += 32;
    _data[i] = (byte) (isMaxLock ? 1 : 0);
    ++i;
    putInt64LE(_data, i, partialUnstakingAmount);
    i += 8;
    putInt64LE(_data, i, padding);
    i += 8;
    i += Borsh.writeArray(buffers, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
