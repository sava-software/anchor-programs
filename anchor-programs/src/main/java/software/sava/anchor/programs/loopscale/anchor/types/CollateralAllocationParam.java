package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CollateralAllocationParam(PublicKey assetIdentifier, long currentAllocationAmount) implements Borsh {

  public static final int BYTES = 40;

  public static CollateralAllocationParam read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var assetIdentifier = readPubKey(_data, i);
    i += 32;
    final var currentAllocationAmount = getInt64LE(_data, i);
    return new CollateralAllocationParam(assetIdentifier, currentAllocationAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    assetIdentifier.write(_data, i);
    i += 32;
    putInt64LE(_data, i, currentAllocationAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
