package software.sava.anchor.programs.glam.kamino.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

// Represents a delegate's permissions for a specific protocol
public record ProtocolPermissions(int protocolBitflag, long permissionsBitmask) implements Borsh {

  public static final int BYTES = 10;

  public static ProtocolPermissions read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var protocolBitflag = getInt16LE(_data, i);
    i += 2;
    final var permissionsBitmask = getInt64LE(_data, i);
    return new ProtocolPermissions(protocolBitflag, permissionsBitmask);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt16LE(_data, i, protocolBitflag);
    i += 2;
    putInt64LE(_data, i, permissionsBitmask);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
