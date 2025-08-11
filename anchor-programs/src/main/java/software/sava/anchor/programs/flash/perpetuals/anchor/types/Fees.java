package software.sava.anchor.programs.flash.perpetuals.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record Fees(FeesMode mode,
                   RatioFees swapIn,
                   RatioFees swapOut,
                   RatioFees stableSwapIn,
                   RatioFees stableSwapOut,
                   RatioFees addLiquidity,
                   RatioFees removeLiquidity,
                   long openPosition,
                   long closePosition,
                   long volatility) implements Borsh {

  public static final int BYTES = 169;

  public static Fees read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mode = FeesMode.read(_data, i);
    i += Borsh.len(mode);
    final var swapIn = RatioFees.read(_data, i);
    i += Borsh.len(swapIn);
    final var swapOut = RatioFees.read(_data, i);
    i += Borsh.len(swapOut);
    final var stableSwapIn = RatioFees.read(_data, i);
    i += Borsh.len(stableSwapIn);
    final var stableSwapOut = RatioFees.read(_data, i);
    i += Borsh.len(stableSwapOut);
    final var addLiquidity = RatioFees.read(_data, i);
    i += Borsh.len(addLiquidity);
    final var removeLiquidity = RatioFees.read(_data, i);
    i += Borsh.len(removeLiquidity);
    final var openPosition = getInt64LE(_data, i);
    i += 8;
    final var closePosition = getInt64LE(_data, i);
    i += 8;
    final var volatility = getInt64LE(_data, i);
    return new Fees(mode,
                    swapIn,
                    swapOut,
                    stableSwapIn,
                    stableSwapOut,
                    addLiquidity,
                    removeLiquidity,
                    openPosition,
                    closePosition,
                    volatility);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(mode, _data, i);
    i += Borsh.write(swapIn, _data, i);
    i += Borsh.write(swapOut, _data, i);
    i += Borsh.write(stableSwapIn, _data, i);
    i += Borsh.write(stableSwapOut, _data, i);
    i += Borsh.write(addLiquidity, _data, i);
    i += Borsh.write(removeLiquidity, _data, i);
    putInt64LE(_data, i, openPosition);
    i += 8;
    putInt64LE(_data, i, closePosition);
    i += 8;
    putInt64LE(_data, i, volatility);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
