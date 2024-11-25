package software.sava.anchor.programs.moon.anchor.types;


import static java.nio.charset.StandardCharsets.UTF_8;
import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

public record TradeEvent(long amount,
                         long collateralAmount,
                         long dexFee,
                         long helioFee,
                         long allocation,
                         PublicKey curve,
                         PublicKey costToken,
                         PublicKey sender,
                         TradeType type,
                         String label, byte[] _label) implements Borsh {

  public static TradeEvent createRecord(final long amount,
                                        final long collateralAmount,
                                        final long dexFee,
                                        final long helioFee,
                                        final long allocation,
                                        final PublicKey curve,
                                        final PublicKey costToken,
                                        final PublicKey sender,
                                        final TradeType type,
                                        final String label) {
    return new TradeEvent(amount,
                          collateralAmount,
                          dexFee,
                          helioFee,
                          allocation,
                          curve,
                          costToken,
                          sender,
                          type,
                          label, label.getBytes(UTF_8));
  }

  public static TradeEvent read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amount = getInt64LE(_data, i);
    i += 8;
    final var collateralAmount = getInt64LE(_data, i);
    i += 8;
    final var dexFee = getInt64LE(_data, i);
    i += 8;
    final var helioFee = getInt64LE(_data, i);
    i += 8;
    final var allocation = getInt64LE(_data, i);
    i += 8;
    final var curve = readPubKey(_data, i);
    i += 32;
    final var costToken = readPubKey(_data, i);
    i += 32;
    final var sender = readPubKey(_data, i);
    i += 32;
    final var type = TradeType.read(_data, i);
    i += Borsh.len(type);
    final var label = Borsh.string(_data, i);
    return new TradeEvent(amount,
                          collateralAmount,
                          dexFee,
                          helioFee,
                          allocation,
                          curve,
                          costToken,
                          sender,
                          type,
                          label, label.getBytes(UTF_8));
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amount);
    i += 8;
    putInt64LE(_data, i, collateralAmount);
    i += 8;
    putInt64LE(_data, i, dexFee);
    i += 8;
    putInt64LE(_data, i, helioFee);
    i += 8;
    putInt64LE(_data, i, allocation);
    i += 8;
    curve.write(_data, i);
    i += 32;
    costToken.write(_data, i);
    i += 32;
    sender.write(_data, i);
    i += 32;
    i += Borsh.write(type, _data, i);
    i += Borsh.writeVector(_label, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 8
         + 8
         + 8
         + 8
         + 8
         + 32
         + 32
         + 32
         + Borsh.len(type)
         + Borsh.lenVector(_label);
  }
}
