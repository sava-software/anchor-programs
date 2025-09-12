package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public record SeedsVec(byte[][] seeds) implements Borsh {

  public static SeedsVec read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var seeds = Borsh.readMultiDimensionbyteVector(_data, offset);
    return new SeedsVec(seeds);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVector(seeds, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVector(seeds);
  }
}
