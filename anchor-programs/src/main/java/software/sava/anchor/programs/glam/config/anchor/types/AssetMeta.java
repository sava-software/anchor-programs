package software.sava.anchor.programs.glam.config.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;

public record AssetMeta(PublicKey asset,
                        int decimals,
                        PublicKey oracle,
                        OracleSource oracleSource,
                        PublicKey aggOracle,
                        OracleSource aggOracleSource,
                        short[] aggIndexes,
                        int maxAgeSeconds,
                        byte[] padding) implements Borsh {

  public static final int BYTES = 128;
  public static final int AGG_INDEXES_LEN = 8;
  public static final int PADDING_LEN = 11;

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
    final var aggOracle = readPubKey(_data, i);
    i += 32;
    final var aggOracleSource = OracleSource.read(_data, i);
    i += Borsh.len(aggOracleSource);
    final var aggIndexes = new short[8];
    i += Borsh.readArray(aggIndexes, _data, i);
    final var maxAgeSeconds = getInt16LE(_data, i);
    i += 2;
    final var padding = new byte[11];
    Borsh.readArray(padding, _data, i);
    return new AssetMeta(asset,
                         decimals,
                         oracle,
                         oracleSource,
                         aggOracle,
                         aggOracleSource,
                         aggIndexes,
                         maxAgeSeconds,
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
    aggOracle.write(_data, i);
    i += 32;
    i += Borsh.write(aggOracleSource, _data, i);
    i += Borsh.writeArray(aggIndexes, _data, i);
    putInt16LE(_data, i, maxAgeSeconds);
    i += 2;
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
