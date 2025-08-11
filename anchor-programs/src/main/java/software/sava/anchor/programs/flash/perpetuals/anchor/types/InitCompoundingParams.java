package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record InitCompoundingParams(long feeShareBps,
                                    String metadataTitle, byte[] _metadataTitle,
                                    String metadataSymbol, byte[] _metadataSymbol,
                                    String metadataUri, byte[] _metadataUri) implements Borsh {

  public static InitCompoundingParams createRecord(final long feeShareBps,
                                                   final String metadataTitle,
                                                   final String metadataSymbol,
                                                   final String metadataUri) {
    return new InitCompoundingParams(feeShareBps,
                                     metadataTitle, metadataTitle.getBytes(UTF_8),
                                     metadataSymbol, metadataSymbol.getBytes(UTF_8),
                                     metadataUri, metadataUri.getBytes(UTF_8));
  }

  public static InitCompoundingParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var feeShareBps = getInt64LE(_data, i);
    i += 8;
    final var metadataTitle = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var metadataSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var metadataUri = Borsh.string(_data, i);
    return new InitCompoundingParams(feeShareBps,
                                     metadataTitle, metadataTitle.getBytes(UTF_8),
                                     metadataSymbol, metadataSymbol.getBytes(UTF_8),
                                     metadataUri, metadataUri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, feeShareBps);
    i += 8;
    i += Borsh.writeVector(_metadataTitle, _data, i);
    i += Borsh.writeVector(_metadataSymbol, _data, i);
    i += Borsh.writeVector(_metadataUri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(_metadataTitle) + Borsh.lenVector(_metadataSymbol) + Borsh.lenVector(_metadataUri);
  }
}
