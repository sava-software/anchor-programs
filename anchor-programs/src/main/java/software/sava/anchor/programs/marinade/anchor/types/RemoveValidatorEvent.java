package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RemoveValidatorEvent(PublicKey state,
                                   PublicKey validator,
                                   int index,
                                   long operationalSolBalance) implements Borsh {

  public static final int BYTES = 76;

  public static RemoveValidatorEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var state = readPubKey(_data, i);
    i += 32;
    final var validator = readPubKey(_data, i);
    i += 32;
    final var index = getInt32LE(_data, i);
    i += 4;
    final var operationalSolBalance = getInt64LE(_data, i);
    return new RemoveValidatorEvent(state,
                                    validator,
                                    index,
                                    operationalSolBalance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    state.write(_data, i);
    i += 32;
    validator.write(_data, i);
    i += 32;
    putInt32LE(_data, i, index);
    i += 4;
    putInt64LE(_data, i, operationalSolBalance);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
