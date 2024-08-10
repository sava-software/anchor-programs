package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RedelegateEvent(PublicKey state,
                              long epoch,
                              int stakeIndex,
                              PublicKey stakeAccount,
                              long lastUpdateDelegation,
                              int sourceValidatorIndex,
                              PublicKey sourceValidatorVote,
                              int sourceValidatorScore,
                              long sourceValidatorBalance,
                              long sourceValidatorStakeTarget,
                              int destValidatorIndex,
                              PublicKey destValidatorVote,
                              int destValidatorScore,
                              long destValidatorBalance,
                              long destValidatorStakeTarget,
                              long redelegateAmount,
                              SplitStakeAccountInfo splitStakeAccount,
                              int redelegateStakeIndex,
                              PublicKey redelegateStakeAccount) implements Borsh {


  public static RedelegateEvent read(final byte[] _data, final int offset) {
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var stakeAccount = readPubKey(_data, i);
    i += 32;
    final var lastUpdateDelegation = getInt64LE(_data, i);
    i += 8;
    final var sourceValidatorIndex = getInt32LE(_data, i);
    i += 4;
    final var sourceValidatorVote = readPubKey(_data, i);
    i += 32;
    final var sourceValidatorScore = getInt32LE(_data, i);
    i += 4;
    final var sourceValidatorBalance = getInt64LE(_data, i);
    i += 8;
    final var sourceValidatorStakeTarget = getInt64LE(_data, i);
    i += 8;
    final var destValidatorIndex = getInt32LE(_data, i);
    i += 4;
    final var destValidatorVote = readPubKey(_data, i);
    i += 32;
    final var destValidatorScore = getInt32LE(_data, i);
    i += 4;
    final var destValidatorBalance = getInt64LE(_data, i);
    i += 8;
    final var destValidatorStakeTarget = getInt64LE(_data, i);
    i += 8;
    final var redelegateAmount = getInt64LE(_data, i);
    i += 8;
    final var splitStakeAccount = _data[i++] == 0 ? null : SplitStakeAccountInfo.read(_data, i);
    if (splitStakeAccount != null) {
      i += Borsh.len(splitStakeAccount);
    }
    final var redelegateStakeIndex = getInt32LE(_data, i);
    i += 4;
    final var redelegateStakeAccount = readPubKey(_data, i);
    return new RedelegateEvent(state,
                               epoch,
                               stakeIndex,
                               stakeAccount,
                               lastUpdateDelegation,
                               sourceValidatorIndex,
                               sourceValidatorVote,
                               sourceValidatorScore,
                               sourceValidatorBalance,
                               sourceValidatorStakeTarget,
                               destValidatorIndex,
                               destValidatorVote,
                               destValidatorScore,
                               destValidatorBalance,
                               destValidatorStakeTarget,
                               redelegateAmount,
                               splitStakeAccount,
                               redelegateStakeIndex,
                               redelegateStakeAccount);
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
    putInt64LE(_data, i, lastUpdateDelegation);
    i += 8;
    putInt32LE(_data, i, sourceValidatorIndex);
    i += 4;
    sourceValidatorVote.write(_data, i);
    i += 32;
    putInt32LE(_data, i, sourceValidatorScore);
    i += 4;
    putInt64LE(_data, i, sourceValidatorBalance);
    i += 8;
    putInt64LE(_data, i, sourceValidatorStakeTarget);
    i += 8;
    putInt32LE(_data, i, destValidatorIndex);
    i += 4;
    destValidatorVote.write(_data, i);
    i += 32;
    putInt32LE(_data, i, destValidatorScore);
    i += 4;
    putInt64LE(_data, i, destValidatorBalance);
    i += 8;
    putInt64LE(_data, i, destValidatorStakeTarget);
    i += 8;
    putInt64LE(_data, i, redelegateAmount);
    i += 8;
    i += Borsh.writeOptional(splitStakeAccount, _data, i);
    putInt32LE(_data, i, redelegateStakeIndex);
    i += 4;
    redelegateStakeAccount.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return 32
         + 8
         + 4
         + 32
         + 8
         + 4
         + 32
         + 4
         + 8
         + 8
         + 4
         + 32
         + 4
         + 8
         + 8
         + 8
         + Borsh.lenOptional(splitStakeAccount)
         + 4
         + 32;
  }
}