package software.sava.anchor.programs.pump.anchor.types;

import java.lang.String;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record CreateEvent(String name, byte[] _name,
                          String symbol, byte[] _symbol,
                          String uri, byte[] _uri,
                          PublicKey mint,
                          PublicKey bondingCurve,
                          PublicKey user) implements Borsh {

  public static CreateEvent createRecord(final String name,
                                         final String symbol,
                                         final String uri,
                                         final PublicKey mint,
                                         final PublicKey bondingCurve,
                                         final PublicKey user) {
    return new CreateEvent(name, name.getBytes(UTF_8),
                           symbol, symbol.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           mint,
                           bondingCurve,
                           user);
  }

  public static CreateEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var symbol = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var uri = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var mint = readPubKey(_data, i);
    i += 32;
    final var bondingCurve = readPubKey(_data, i);
    i += 32;
    final var user = readPubKey(_data, i);
    return new CreateEvent(name, name.getBytes(UTF_8),
                           symbol, symbol.getBytes(UTF_8),
                           uri, uri.getBytes(UTF_8),
                           mint,
                           bondingCurve,
                           user);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_name, _data, i);
    i += Borsh.writeVector(_symbol, _data, i);
    i += Borsh.writeVector(_uri, _data, i);
    mint.write(_data, i);
    i += 32;
    bondingCurve.write(_data, i);
    i += 32;
    user.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_name)
         + Borsh.lenVector(_symbol)
         + Borsh.lenVector(_uri)
         + 32
         + 32
         + 32;
  }
}
