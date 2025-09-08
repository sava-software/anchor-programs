package software.sava.anchor.programs.glam.spl.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

// Stores policy data for an integrated protocol.
// Integration programs serialize/deserialize this data.
public record ProtocolPolicy(int protocolBitflag, byte[] data) implements Borsh {

  public static ProtocolPolicy read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocolBitflag = getInt16LE(_data, i);
    i += 2;
    final var data = Borsh.readbyteVector(_data, i);
    return new ProtocolPolicy(protocolBitflag, data);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, protocolBitflag);
    i += 2;
    i += Borsh.writeVector(data, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 2 + Borsh.lenVector(data);
  }
}
