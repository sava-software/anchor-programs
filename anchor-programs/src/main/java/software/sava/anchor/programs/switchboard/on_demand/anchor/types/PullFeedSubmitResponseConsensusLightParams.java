package software.sava.anchor.programs.switchboard.on_demand.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record PullFeedSubmitResponseConsensusLightParams(long slot, BigInteger[] values) implements Borsh {

  public static PullFeedSubmitResponseConsensusLightParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var values = Borsh.read128Vector(_data, i);
    return new PullFeedSubmitResponseConsensusLightParams(slot, values);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    i += Borsh.write128Vector(values, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.len128Vector(values);
  }
}
