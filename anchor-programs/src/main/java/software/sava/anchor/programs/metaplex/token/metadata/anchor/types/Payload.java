package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import java.lang.String;

import software.sava.core.borsh.Borsh;

import static java.nio.charset.StandardCharsets.UTF_8;

import static software.sava.core.encoding.ByteUtil.getInt32LE;

public record Payload(String key, byte[] _key, PayloadType payloadType) implements Borsh {

  public static Payload createRecord(final String key, final PayloadType payloadType) {
    return new Payload(key, key.getBytes(UTF_8), payloadType);
  }

  public static Payload read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var key = Borsh.string(_data, i);
    i += (Integer.BYTES + getInt32LE(_data, i));
    final var payloadType = PayloadType.read(_data, i);
    return new Payload(key, key.getBytes(UTF_8), payloadType);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(_key, _data, i);
    i += Borsh.write(payloadType, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(_key) + Borsh.len(payloadType);
  }
}
