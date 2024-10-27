package software.sava.anchor.programs.drift.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record RFQMakerOrderParams(byte[] uuid,
                                  PublicKey authority,
                                  int subAccountId,
                                  int marketIndex,
                                  MarketType marketType,
                                  long baseAssetAmount,
                                  long price,
                                  PositionDirection direction,
                                  long maxTs) implements Borsh {

  public static final int BYTES = 70;

  public static RFQMakerOrderParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var uuid = new byte[8];
    i += Borsh.readArray(uuid, _data, i);
    final var authority = readPubKey(_data, i);
    i += 32;
    final var subAccountId = getInt16LE(_data, i);
    i += 2;
    final var marketIndex = getInt16LE(_data, i);
    i += 2;
    final var marketType = MarketType.read(_data, i);
    i += Borsh.len(marketType);
    final var baseAssetAmount = getInt64LE(_data, i);
    i += 8;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var direction = PositionDirection.read(_data, i);
    i += Borsh.len(direction);
    final var maxTs = getInt64LE(_data, i);
    return new RFQMakerOrderParams(uuid,
                                   authority,
                                   subAccountId,
                                   marketIndex,
                                   marketType,
                                   baseAssetAmount,
                                   price,
                                   direction,
                                   maxTs);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(uuid, _data, i);
    authority.write(_data, i);
    i += 32;
    putInt16LE(_data, i, subAccountId);
    i += 2;
    putInt16LE(_data, i, marketIndex);
    i += 2;
    i += Borsh.write(marketType, _data, i);
    putInt64LE(_data, i, baseAssetAmount);
    i += 8;
    putInt64LE(_data, i, price);
    i += 8;
    i += Borsh.write(direction, _data, i);
    putInt64LE(_data, i, maxTs);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
