package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CollateralTerms(PublicKey assetIdentifier, long[] terms) implements Borsh {

  public static final int BYTES = 72;
  public static final int TERMS_LEN = 5;

  public static CollateralTerms read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetIdentifier = readPubKey(_data, i);
    i += 32;
    final var terms = new long[5];
    Borsh.readArray(terms, _data, i);
    return new CollateralTerms(assetIdentifier, terms);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    assetIdentifier.write(_data, i);
    i += 32;
    i += Borsh.writeArray(terms, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
