package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SellLedgerParams(int ledgerIndex,
                               long expectedSalePrice,
                               byte[] assetIndexGuidance) implements Borsh {

  public static SellLedgerParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ledgerIndex = _data[i] & 0xFF;
    ++i;
    final var expectedSalePrice = getInt64LE(_data, i);
    i += 8;
    final byte[] assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new SellLedgerParams(ledgerIndex, expectedSalePrice, assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) ledgerIndex;
    ++i;
    putInt64LE(_data, i, expectedSalePrice);
    i += 8;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + 8 + Borsh.lenVector(assetIndexGuidance);
  }
}
