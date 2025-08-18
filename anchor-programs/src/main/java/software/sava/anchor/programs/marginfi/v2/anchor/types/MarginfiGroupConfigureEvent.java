package software.sava.anchor.programs.marginfi.v2.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record MarginfiGroupConfigureEvent(GroupEventHeader header,
                                          PublicKey admin,
                                          long flags) implements Borsh {

  public static MarginfiGroupConfigureEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var header = GroupEventHeader.read(_data, i);
    i += Borsh.len(header);
    final var admin = readPubKey(_data, i);
    i += 32;
    final var flags = getInt64LE(_data, i);
    return new MarginfiGroupConfigureEvent(header, admin, flags);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(header, _data, i);
    admin.write(_data, i);
    i += 32;
    putInt64LE(_data, i, flags);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.len(header) + 32 + 8;
  }
}
