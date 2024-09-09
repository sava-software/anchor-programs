package software.sava.anchor.programs.marinade.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;

public record SetValidatorScoreEvent(PublicKey state,
                                     PublicKey validator,
                                     int index,
                                     U32ValueChange scoreChange) implements Borsh {

  public static final int BYTES = 76;

  public static SetValidatorScoreEvent read(final byte[] _data, final int offset) {
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
    final var scoreChange = U32ValueChange.read(_data, i);
    return new SetValidatorScoreEvent(state,
                                      validator,
                                      index,
                                      scoreChange);
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
    i += Borsh.write(scoreChange, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
