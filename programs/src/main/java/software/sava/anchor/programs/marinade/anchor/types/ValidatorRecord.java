package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record ValidatorRecord(// Validator vote pubkey
                              PublicKey validatorAccount,
                              // Validator total balance in lamports
                              long activeBalance,
                              int score,
                              long lastStakeDeltaEpoch,
                              int duplicationFlagBumpSeed) implements Borsh {

  public static final int BYTES = 53;

  public static ValidatorRecord read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var validatorAccount = readPubKey(_data, i);
    i += 32;
    final var activeBalance = getInt64LE(_data, i);
    i += 8;
    final var score = getInt32LE(_data, i);
    i += 4;
    final var lastStakeDeltaEpoch = getInt64LE(_data, i);
    i += 8;
    final var duplicationFlagBumpSeed = _data[i] & 0xFF;
    return new ValidatorRecord(validatorAccount,
                               activeBalance,
                               score,
                               lastStakeDeltaEpoch,
                               duplicationFlagBumpSeed);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    validatorAccount.write(_data, i);
    i += 32;
    putInt64LE(_data, i, activeBalance);
    i += 8;
    putInt32LE(_data, i, score);
    i += 4;
    putInt64LE(_data, i, lastStakeDeltaEpoch);
    i += 8;
    _data[i] = (byte) duplicationFlagBumpSeed;
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
