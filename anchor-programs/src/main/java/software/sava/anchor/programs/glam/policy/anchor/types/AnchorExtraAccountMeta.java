package software.sava.anchor.programs.glam.policy.anchor.types;

import software.sava.core.borsh.Borsh;

public record AnchorExtraAccountMeta(int discriminator,
                                     byte[] addressConfig,
                                     boolean isSigner,
                                     boolean isWritable) implements Borsh {

  public static final int BYTES = 35;
  public static final int ADDRESS_CONFIG_LEN = 32;

  public static AnchorExtraAccountMeta read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var discriminator = _data[i] & 0xFF;
    ++i;
    final var addressConfig = new byte[32];
    i += Borsh.readArray(addressConfig, _data, i);
    final var isSigner = _data[i] == 1;
    ++i;
    final var isWritable = _data[i] == 1;
    return new AnchorExtraAccountMeta(discriminator,
                                      addressConfig,
                                      isSigner,
                                      isWritable);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) discriminator;
    ++i;
    i += Borsh.writeArray(addressConfig, _data, i);
    _data[i] = (byte) (isSigner ? 1 : 0);
    ++i;
    _data[i] = (byte) (isWritable ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
