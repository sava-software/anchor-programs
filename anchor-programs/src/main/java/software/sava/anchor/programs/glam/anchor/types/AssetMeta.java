package software.sava.anchor.programs.glam.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record AssetMeta(PublicKey asset,
                        int decimals,
                        PublicKey oracle,
                        OracleSource oracleSource,
                        int maxAgeSeconds,
                        int priority,
                        byte[] padding) implements Borsh {

  public static final int BYTES = 72;
  public static final int PADDING_LEN = 3;

  public static AssetMeta read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var asset = readPubKey(_data, i);
    i += 32;
    final var decimals = _data[i] & 0xFF;
    ++i;
    final var oracle = readPubKey(_data, i);
    i += 32;
    final var oracleSource = OracleSource.read(_data, i);
    i += Borsh.len(oracleSource);
    final var maxAgeSeconds = getInt16LE(_data, i);
    i += 2;
    final var priority = _data[i] & 0xFF;
    ++i;
    final var padding = new byte[3];
    Borsh.readArray(padding, _data, i);
    return new AssetMeta(asset,
                         decimals,
                         oracle,
                         oracleSource,
                         maxAgeSeconds,
                         priority,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    asset.write(_data, i);
    i += 32;
    _data[i] = (byte) decimals;
    ++i;
    oracle.write(_data, i);
    i += 32;
    i += Borsh.write(oracleSource, _data, i);
    putInt16LE(_data, i, maxAgeSeconds);
    i += 2;
    _data[i] = (byte) priority;
    ++i;
    i += Borsh.writeArrayChecked(padding, 3, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
