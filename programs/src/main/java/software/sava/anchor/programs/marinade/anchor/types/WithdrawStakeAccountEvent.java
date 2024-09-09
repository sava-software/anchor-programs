package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record WithdrawStakeAccountEvent(PublicKey state,
                                        long epoch,
                                        PublicKey stake,
                                        long lastUpdateStakeDelegation,
                                        int stakeIndex,
                                        PublicKey validator,
                                        int validatorIndex,
                                        long userMsolBalance,
                                        PublicKey userMsolAuth,
                                        long msolBurned,
                                        long msolFees,
                                        PublicKey splitStake,
                                        PublicKey beneficiary,
                                        long splitLamports,
                                        int feeBpCents,
                                        long totalVirtualStakedLamports,
                                        long msolSupply) implements Borsh {

  public static final int BYTES = 268;

  public static WithdrawStakeAccountEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var stake = readPubKey(_data, i);
    i += 32;
    final var lastUpdateStakeDelegation = getInt64LE(_data, i);
    i += 8;
    final var stakeIndex = getInt32LE(_data, i);
    i += 4;
    final var validator = readPubKey(_data, i);
    i += 32;
    final var validatorIndex = getInt32LE(_data, i);
    i += 4;
    final var userMsolBalance = getInt64LE(_data, i);
    i += 8;
    final var userMsolAuth = readPubKey(_data, i);
    i += 32;
    final var msolBurned = getInt64LE(_data, i);
    i += 8;
    final var msolFees = getInt64LE(_data, i);
    i += 8;
    final var splitStake = readPubKey(_data, i);
    i += 32;
    final var beneficiary = readPubKey(_data, i);
    i += 32;
    final var splitLamports = getInt64LE(_data, i);
    i += 8;
    final var feeBpCents = getInt32LE(_data, i);
    i += 4;
    final var totalVirtualStakedLamports = getInt64LE(_data, i);
    i += 8;
    final var msolSupply = getInt64LE(_data, i);
    return new WithdrawStakeAccountEvent(state,
                                         epoch,
                                         stake,
                                         lastUpdateStakeDelegation,
                                         stakeIndex,
                                         validator,
                                         validatorIndex,
                                         userMsolBalance,
                                         userMsolAuth,
                                         msolBurned,
                                         msolFees,
                                         splitStake,
                                         beneficiary,
                                         splitLamports,
                                         feeBpCents,
                                         totalVirtualStakedLamports,
                                         msolSupply);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    putInt64LE(_data, i, epoch);
    i += 8;
    stake.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lastUpdateStakeDelegation);
    i += 8;
    putInt32LE(_data, i, stakeIndex);
    i += 4;
    validator.write(_data, i);
    i += 32;
    putInt32LE(_data, i, validatorIndex);
    i += 4;
    putInt64LE(_data, i, userMsolBalance);
    i += 8;
    userMsolAuth.write(_data, i);
    i += 32;
    putInt64LE(_data, i, msolBurned);
    i += 8;
    putInt64LE(_data, i, msolFees);
    i += 8;
    splitStake.write(_data, i);
    i += 32;
    beneficiary.write(_data, i);
    i += 32;
    putInt64LE(_data, i, splitLamports);
    i += 8;
    putInt32LE(_data, i, feeBpCents);
    i += 4;
    putInt64LE(_data, i, totalVirtualStakedLamports);
    i += 8;
    putInt64LE(_data, i, msolSupply);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
