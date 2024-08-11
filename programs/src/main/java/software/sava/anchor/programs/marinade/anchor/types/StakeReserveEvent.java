package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StakeReserveEvent(PublicKey state,
                                long epoch,
                                int stakeIndex,
                                PublicKey stakeAccount,
                                int validatorIndex,
                                PublicKey validatorVote,
                                long totalStakeTarget,
                                long validatorStakeTarget,
                                long reserveBalance,
                                long totalActiveBalance,
                                long validatorActiveBalance,
                                long totalStakeDelta,
                                long amount) implements Borsh {

  public static final int BYTES = 168;

  public static StakeReserveEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var stakeAccount = readPubKey(_data, i);
    i += 32;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var validatorVote = readPubKey(_data, i);
    i += 32;
    final var totalStakeTarget = getInt64LE(_data, i);
    i += 8;
    final var validatorStakeTarget = getInt64LE(_data, i);
    i += 8;
    final var reserveBalance = getInt64LE(_data, i);
    i += 8;
    final var totalActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var validatorActiveBalance = getInt64LE(_data, i);
    i += 8;
    final var totalStakeDelta = getInt64LE(_data, i);
    i += 8;
    final var amount = getInt64LE(_data, i);
    return new StakeReserveEvent(state,
                                 epoch,
                                 stakeIndex,
                                 stakeAccount,
                                 validatorIndex,
                                 validatorVote,
                                 totalStakeTarget,
                                 validatorStakeTarget,
                                 reserveBalance,
                                 totalActiveBalance,
                                 validatorActiveBalance,
                                 totalStakeDelta,
                                 amount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    stakeAccount.write(_data, i);
    i += 32;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    validatorVote.write(_data, i);
    i += 32;
    putInt64LE(_data, i, totalStakeTarget);
    i += 8;
    putInt64LE(_data, i, validatorStakeTarget);
    i += 8;
    putInt64LE(_data, i, reserveBalance);
    i += 8;
    putInt64LE(_data, i, totalActiveBalance);
    i += 8;
    putInt64LE(_data, i, validatorActiveBalance);
    i += 8;
    putInt64LE(_data, i, totalStakeDelta);
    i += 8;
    putInt64LE(_data, i, amount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 4
         + 32
         + 4
         + 32
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8
         + 8;
  }
}
