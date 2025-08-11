package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RenameFlpParams(long flag,
                              String lpTokenName, byte[] _lpTokenName,
                              String lpTokenSymbol, byte[] _lpTokenSymbol,
                              String lpTokenUri, byte[] _lpTokenUri) implements Borsh {

  public static RenameFlpParams createRecord(final long flag,
                                             final String lpTokenName,
                                             final String lpTokenSymbol,
                                             final String lpTokenUri) {
    return new RenameFlpParams(flag,
                               lpTokenName, lpTokenName.getBytes(UTF_8),
                               lpTokenSymbol, lpTokenSymbol.getBytes(UTF_8),
                               lpTokenUri, lpTokenUri.getBytes(UTF_8));
  }

  public static RenameFlpParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var flag = getInt64LE(_data, i);
    i += 8;
    final var lpTokenName = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var lpTokenSymbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var lpTokenUri = Borsh.string(_data, i);
    return new RenameFlpParams(flag,
                               lpTokenName, lpTokenName.getBytes(UTF_8),
                               lpTokenSymbol, lpTokenSymbol.getBytes(UTF_8),
                               lpTokenUri, lpTokenUri.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, flag);
    i += 8;
    i += Borsh.writeVector(_lpTokenName, _data, i);
    i += Borsh.writeVector(_lpTokenSymbol, _data, i);
    i += Borsh.writeVector(_lpTokenUri, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8 + Borsh.lenVector(_lpTokenName) + Borsh.lenVector(_lpTokenSymbol) + Borsh.lenVector(_lpTokenUri);
  }
}
