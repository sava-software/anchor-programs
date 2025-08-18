package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;

public record CollateralData(PublicKey assetMint,
                             PodU64 amount,
                             int assetType,
                             PublicKey assetIdentifier) implements Borsh {

  public static final int BYTES = 73;

  public static CollateralData read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetMint = readPubKey(_data, i);
    i += 32;
    final var amount = PodU64.read(_data, i);
    i += Borsh.len(amount);
    final var assetType = _data[i] & 0xFF;
    ++i;
    final var assetIdentifier = readPubKey(_data, i);
    return new CollateralData(assetMint,
                              amount,
                              assetType,
                              assetIdentifier);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    assetMint.write(_data, i);
    i += 32;
    i += Borsh.write(amount, _data, i);
    _data[i] = (byte) assetType;
    ++i;
    assetIdentifier.write(_data, i);
    i += 32;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
