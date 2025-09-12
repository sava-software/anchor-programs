package software.sava.anchor.programs.metaplex.token.metadata.anchor.types;

import software.sava.core.borsh.Borsh;

public record ProofInfo(byte[][] proof) implements Borsh {

  public static ProofInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    final var proof = Borsh.readMultiDimensionbyteVectorArray(32, _data, offset);
    return new ProofInfo(proof);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeVectorArray(proof, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return Borsh.lenVectorArray(proof);
  }
}
