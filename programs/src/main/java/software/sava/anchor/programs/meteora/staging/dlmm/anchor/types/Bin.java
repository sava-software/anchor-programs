package software.sava.anchor.programs.meteora.staging.dlmm.anchor.types;

import java.math.BigInteger;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Bin(// Amount of token X in the bin. This already excluded protocol fees.
                  long amountX,
                  // Amount of token Y in the bin. This already excluded protocol fees.
                  long amountY,
                  // Bin price
                  BigInteger price,
                  // Liquidities of the bin. This is the same as LP mint supply. q-number
                  BigInteger liquiditySupply,
                  // reward_a_per_token_stored
                  BigInteger[] rewardPerTokenStored,
                  // Swap fee amount of token X per liquidity deposited.
                  BigInteger feeAmountXPerTokenStored,
                  // Swap fee amount of token Y per liquidity deposited.
                  BigInteger feeAmountYPerTokenStored,
                  // Total token X swap into the bin. Only used for tracking purpose.
                  BigInteger amountXIn,
                  // Total token Y swap into he bin. Only used for tracking purpose.
                  BigInteger amountYIn) implements Borsh {

  public static final int BYTES = 144;

  public static Bin read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var amountX = getInt64LE(_data, i);
    i += 8;
    final var amountY = getInt64LE(_data, i);
    i += 8;
    final var price = getInt128LE(_data, i);
    i += 16;
    final var liquiditySupply = getInt128LE(_data, i);
    i += 16;
    final var rewardPerTokenStored = new BigInteger[2];
    i += Borsh.readArray(rewardPerTokenStored, _data, i);
    final var feeAmountXPerTokenStored = getInt128LE(_data, i);
    i += 16;
    final var feeAmountYPerTokenStored = getInt128LE(_data, i);
    i += 16;
    final var amountXIn = getInt128LE(_data, i);
    i += 16;
    final var amountYIn = getInt128LE(_data, i);
    return new Bin(amountX,
                   amountY,
                   price,
                   liquiditySupply,
                   rewardPerTokenStored,
                   feeAmountXPerTokenStored,
                   feeAmountYPerTokenStored,
                   amountXIn,
                   amountYIn);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, amountX);
    i += 8;
    putInt64LE(_data, i, amountY);
    i += 8;
    putInt128LE(_data, i, price);
    i += 16;
    putInt128LE(_data, i, liquiditySupply);
    i += 16;
    i += Borsh.writeArray(rewardPerTokenStored, _data, i);
    putInt128LE(_data, i, feeAmountXPerTokenStored);
    i += 16;
    putInt128LE(_data, i, feeAmountYPerTokenStored);
    i += 16;
    putInt128LE(_data, i, amountXIn);
    i += 16;
    putInt128LE(_data, i, amountYIn);
    i += 16;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
