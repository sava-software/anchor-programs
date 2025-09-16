package software.sava.anchor.programs.glam.kamino.anchor.types;

import software.sava.core.borsh.Borsh;

// Vault-specific oracle configs. If available, these configs are preferred over the global config.
public record OracleConfigs(short[][] maxAgesSeconds, byte[] padding) implements Borsh {

  public static final int PADDING_LEN = 12;
  public static OracleConfigs read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxAgesSeconds = Borsh.readMultiDimensionshortVectorArray(2, _data, i);
    i += Borsh.lenVectorArray(maxAgesSeconds);
    final var padding = new byte[12];
    Borsh.readArray(padding, _data, i);
    return new OracleConfigs(maxAgesSeconds, padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVectorArray(maxAgesSeconds, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVectorArray(maxAgesSeconds) + Borsh.lenArray(padding);
  }
}
