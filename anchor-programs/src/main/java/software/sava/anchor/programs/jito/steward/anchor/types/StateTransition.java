package software.sava.anchor.programs.jito.steward.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record StateTransition(long epoch,
                              long slot,
                              String previousState, byte[] _previousState,
                              String newState, byte[] _newState) implements Borsh {

  public static StateTransition createRecord(final long epoch,
                                             final long slot,
                                             final String previousState,
                                             final String newState) {
    return new StateTransition(epoch,
                               slot,
                               previousState, previousState.getBytes(UTF_8),
                               newState, newState.getBytes(UTF_8));
  }

  public static StateTransition read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var epoch = getInt64LE(_data, i);
    i += 8;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var previousState = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var newState = Borsh.string(_data, i);
    return new StateTransition(epoch,
                               slot,
                               previousState, previousState.getBytes(UTF_8),
                               newState, newState.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, epoch);
    i += 8;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.writeVector(_previousState, _data, i);
    i += Borsh.writeVector(_newState, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + 8 + Borsh.lenVector(_previousState) + Borsh.lenVector(_newState);
  }
}
