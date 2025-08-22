package software.sava.anchor.programs.chainlink.ocr2.anchor.types;

import software.sava.core.borsh.Borsh;

public record SigningKey(byte[] key) implements Borsh {

  public static final int BYTES = 20;
  public static final int KEY_LEN = 20;

  public static SigningKey read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var key = new byte[20];
    Borsh.readArray(key, _data, offset);
    return new SigningKey(key);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(key, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
