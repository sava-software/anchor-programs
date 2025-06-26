package software.sava.anchor.programs.glam.config.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record AssetMeta(PublicKey asset,
                        int decimals,
                        PublicKey oracle,
                        OracleSource oracleSource) implements Borsh {

  public static final int BYTES = 66;

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
    return new AssetMeta(asset,
                         decimals,
                         oracle,
                         oracleSource);
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
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
