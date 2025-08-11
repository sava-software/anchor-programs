package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record DecreaseSizeLog(PublicKey owner,
                              PublicKey market,
                              long priceUsd,
                              long sizeDelta,
                              long sizeDeltaUsd,
                              long settledReturns,
                              long deltaProfitUsd,
                              long deltaLossUsd,
                              long feeAmount) implements Borsh {

  public static final int BYTES = 120;

  public static DecreaseSizeLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var owner = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var priceUsd = getInt64LE(_data, i);
    i += 8;
    final var sizeDelta = getInt64LE(_data, i);
    i += 8;
    final var sizeDeltaUsd = getInt64LE(_data, i);
    i += 8;
    final var settledReturns = getInt64LE(_data, i);
    i += 8;
    final var deltaProfitUsd = getInt64LE(_data, i);
    i += 8;
    final var deltaLossUsd = getInt64LE(_data, i);
    i += 8;
    final var feeAmount = getInt64LE(_data, i);
    return new DecreaseSizeLog(owner,
                               market,
                               priceUsd,
                               sizeDelta,
                               sizeDeltaUsd,
                               settledReturns,
                               deltaProfitUsd,
                               deltaLossUsd,
                               feeAmount);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    owner.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    putInt64LE(_data, i, priceUsd);
    i += 8;
    putInt64LE(_data, i, sizeDelta);
    i += 8;
    putInt64LE(_data, i, sizeDeltaUsd);
    i += 8;
    putInt64LE(_data, i, settledReturns);
    i += 8;
    putInt64LE(_data, i, deltaProfitUsd);
    i += 8;
    putInt64LE(_data, i, deltaLossUsd);
    i += 8;
    putInt64LE(_data, i, feeAmount);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
