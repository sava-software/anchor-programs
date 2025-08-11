package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CancelTriggerOrderLog(PublicKey owner,
                                    PublicKey market,
                                    long price,
                                    int priceExponent,
                                    long sizeAmount,
                                    int receiveCustodyUid,
                                    boolean isStopLoss) implements Borsh {

  public static final int BYTES = 86;

  public static CancelTriggerOrderLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var price = getInt64LE(_data, i);
    i += 8;
    final var priceExponent = getInt32LE(_data, i);
    i += 4;
    final var sizeAmount = getInt64LE(_data, i);
    i += 8;
    final var receiveCustodyUid = _data[i] & 0xFF;
    ++i;
    final var isStopLoss = _data[i] == 1;
    return new CancelTriggerOrderLog(owner,
                                     market,
                                     price,
                                     priceExponent,
                                     sizeAmount,
                                     receiveCustodyUid,
                                     isStopLoss);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    putInt64LE(_data, i, price);
    i += 8;
    putInt32LE(_data, i, priceExponent);
    i += 4;
    putInt64LE(_data, i, sizeAmount);
    i += 8;
    _data[i] = (byte) receiveCustodyUid;
    ++i;
    _data[i] = (byte) (isStopLoss ? 1 : 0);
    ++i;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
