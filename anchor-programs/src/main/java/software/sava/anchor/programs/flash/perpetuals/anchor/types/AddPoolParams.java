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
                            int vpVolumeFactor) implements Borsh {

  public static AddPoolParams createRecord(final String name,
                                           final Permissions permissions,
                                           final long maxAumUsd,
                                           final String metadataTitle,
                                           final String metadataSymbol,
                                           final String metadataUri,
                                           final long stakingFeeShareBps,
                                           final int vpVolumeFactor) {
    return new AddPoolParams(name, name.getBytes(UTF_8),
                             permissions,
                             maxAumUsd,
                             metadataTitle, metadataTitle.getBytes(UTF_8),
                             metadataSymbol, metadataSymbol.getBytes(UTF_8),
                             metadataUri, metadataUri.getBytes(UTF_8),
                             stakingFeeShareBps,
                             vpVolumeFactor);
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
    return new AddPoolParams(name, name.getBytes(UTF_8),
                             permissions,
                             maxAumUsd,
                             metadataTitle, metadataTitle.getBytes(UTF_8),
                             metadataSymbol, metadataSymbol.getBytes(UTF_8),
                             metadataUri, metadataUri.getBytes(UTF_8),
                             stakingFeeShareBps,
                             vpVolumeFactor);
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
         + 1;
  }
}
