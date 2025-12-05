package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AddPoolParams(String name, byte[] _name,
                            Permissions permissions,
                            long maxAumUsd,
                            String metadataTitle, byte[] _metadataTitle,
                            String metadataSymbol, byte[] _metadataSymbol,
                            String metadataUri, byte[] _metadataUri,
                            long stakingFeeShareBps,
                            int vpVolumeFactor,
                            long[] stakingFeeBoostBps,
                            long minLpPriceUsd,
                            long maxLpPriceUsd,
                            long thresholdUsd) implements Borsh {

  public static final int STAKING_FEE_BOOST_BPS_LEN = 6;
  public static AddPoolParams createRecord(final String name,
                                           final Permissions permissions,
                                           final long maxAumUsd,
                                           final String metadataTitle,
                                           final String metadataSymbol,
                                           final String metadataUri,
                                           final long stakingFeeShareBps,
                                           final int vpVolumeFactor,
                                           final long[] stakingFeeBoostBps,
                                           final long minLpPriceUsd,
                                           final long maxLpPriceUsd,
                                           final long thresholdUsd) {
    return new AddPoolParams(name, name.getBytes(UTF_8),
                             permissions,
                             maxAumUsd,
                             metadataTitle, metadataTitle.getBytes(UTF_8),
                             metadataSymbol, metadataSymbol.getBytes(UTF_8),
                             metadataUri, metadataUri.getBytes(UTF_8),
                             stakingFeeShareBps,
                             vpVolumeFactor,
                             stakingFeeBoostBps,
                             minLpPriceUsd,
                             maxLpPriceUsd,
                             thresholdUsd);
  }

  public static AddPoolParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var permissions = Permissions.read(_data, i);
    i += Borsh.len(permissions);
    final var maxAumUsd = getInt64LE(_data, i);
    i += 8;
    final var metadataTitle = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var metadataSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var metadataUri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var stakingFeeShareBps = getInt64LE(_data, i);
    i += 8;
    final var vpVolumeFactor = _data[i] & 0xFF;
    ++i;
    final var stakingFeeBoostBps = new long[6];
    i += Borsh.readArray(stakingFeeBoostBps, _data, i);
    final var minLpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var maxLpPriceUsd = getInt64LE(_data, i);
    i += 8;
    final var thresholdUsd = getInt64LE(_data, i);
    return new AddPoolParams(name, name.getBytes(UTF_8),
                             permissions,
                             maxAumUsd,
                             metadataTitle, metadataTitle.getBytes(UTF_8),
                             metadataSymbol, metadataSymbol.getBytes(UTF_8),
                             metadataUri, metadataUri.getBytes(UTF_8),
                             stakingFeeShareBps,
                             vpVolumeFactor,
                             stakingFeeBoostBps,
                             minLpPriceUsd,
                             maxLpPriceUsd,
                             thresholdUsd);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.write(permissions, _data, i);
    putInt64LE(_data, i, maxAumUsd);
    i += 8;
    i += Borsh.writeVector(_metadataTitle, _data, i);
    i += Borsh.writeVector(_metadataSymbol, _data, i);
    i += Borsh.writeVector(_metadataUri, _data, i);
    putInt64LE(_data, i, stakingFeeShareBps);
    i += 8;
    _data[i] = (byte) vpVolumeFactor;
    ++i;
    i += Borsh.writeArrayChecked(stakingFeeBoostBps, 6, _data, i);
    putInt64LE(_data, i, minLpPriceUsd);
    i += 8;
    putInt64LE(_data, i, maxLpPriceUsd);
    i += 8;
    putInt64LE(_data, i, thresholdUsd);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.len(permissions)
         + 8
         + Borsh.lenVector(_metadataTitle)
         + Borsh.lenVector(_metadataSymbol)
         + Borsh.lenVector(_metadataUri)
         + 8
         + 1
         + Borsh.lenArray(stakingFeeBoostBps)
         + 8
         + 8
         + 8;
  }
}
