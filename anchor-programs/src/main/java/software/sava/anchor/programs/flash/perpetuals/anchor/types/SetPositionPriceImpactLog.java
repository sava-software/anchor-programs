package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record SetPositionPriceImpactLog(PublicKey position,
                                        PublicKey market,
                                        long priceImpactUsd,
                                        long[] padding) implements Borsh {

  public static final int BYTES = 104;
  public static final int PADDING_LEN = 4;

  public static SetPositionPriceImpactLog read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var position = readPubKey(_data, i);
    i += 32;
    final var market = readPubKey(_data, i);
    i += 32;
    final var priceImpactUsd = getInt64LE(_data, i);
    i += 8;
    final var padding = new long[4];
    Borsh.readArray(padding, _data, i);
    return new SetPositionPriceImpactLog(position,
                                         market,
                                         priceImpactUsd,
                                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    position.write(_data, i);
    i += 32;
    market.write(_data, i);
    i += 32;
    putInt64LE(_data, i, priceImpactUsd);
    i += 8;
    i += Borsh.writeArrayChecked(padding, 4, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
