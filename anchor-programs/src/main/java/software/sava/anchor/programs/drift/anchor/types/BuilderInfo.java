package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record BuilderInfo(PublicKey authority,
                          int maxFeeTenthBps,
                          byte[] padding) implements Borsh {

  public static final int BYTES = 40;
  public static final int PADDING_LEN = 6;

  public static BuilderInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var authority = readPubKey(_data, i);
    i += 32;
    final var maxFeeTenthBps = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[6];
    Borsh.readArray(padding, _data, i);
    return new BuilderInfo(authority, maxFeeTenthBps, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    authority.write(_data, i);
    i += 32;
    putInt16LE(_data, i, maxFeeTenthBps);
    i += 2;
    i += Borsh.writeArrayChecked(padding, 6, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
