package software.sava.anchor.programs.kamino.liquidity.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CollateralInfo(PublicKey mint,
                             long lowerHeuristic,
                             long upperHeuristic,
                             long expHeuristic,
                             long maxTwapDivergenceBps,
                             short[] scopeTwapPriceChain,
                             short[] scopePriceChain,
                             byte[] name,
                             long maxAgePriceSeconds,
                             long maxAgeTwapSeconds,
                             long maxIgnorableAmountAsReward,
                             int disabled,
                             byte[] padding0,
                             short[] scopeStakingRateChain,
                             long[] padding) implements Borsh {

  public static final int BYTES = 216;

  public static CollateralInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var mint = readPubKey(_data, i);
    i += 32;
    final var lowerHeuristic = getInt64LE(_data, i);
    i += 8;
    final var upperHeuristic = getInt64LE(_data, i);
    i += 8;
    final var expHeuristic = getInt64LE(_data, i);
    i += 8;
    final var maxTwapDivergenceBps = getInt64LE(_data, i);
    i += 8;
    final var scopeTwapPriceChain = new short[4];
    i += Borsh.readArray(scopeTwapPriceChain, _data, i);
    final var scopePriceChain = new short[4];
    i += Borsh.readArray(scopePriceChain, _data, i);
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var maxAgePriceSeconds = getInt64LE(_data, i);
    i += 8;
    final var maxAgeTwapSeconds = getInt64LE(_data, i);
    i += 8;
    final var maxIgnorableAmountAsReward = getInt64LE(_data, i);
    i += 8;
    final var disabled = _data[i] & 0xFF;
    ++i;
    final var padding0 = new byte[7];
    i += Borsh.readArray(padding0, _data, i);
    final var scopeStakingRateChain = new short[4];
    i += Borsh.readArray(scopeStakingRateChain, _data, i);
    final var padding = new long[8];
    Borsh.readArray(padding, _data, i);
    return new CollateralInfo(mint,
                              lowerHeuristic,
                              upperHeuristic,
                              expHeuristic,
                              maxTwapDivergenceBps,
                              scopeTwapPriceChain,
                              scopePriceChain,
                              name,
                              maxAgePriceSeconds,
                              maxAgeTwapSeconds,
                              maxIgnorableAmountAsReward,
                              disabled,
                              padding0,
                              scopeStakingRateChain,
                              padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    mint.write(_data, i);
    i += 32;
    putInt64LE(_data, i, lowerHeuristic);
    i += 8;
    putInt64LE(_data, i, upperHeuristic);
    i += 8;
    putInt64LE(_data, i, expHeuristic);
    i += 8;
    putInt64LE(_data, i, maxTwapDivergenceBps);
    i += 8;
    i += Borsh.writeArray(scopeTwapPriceChain, _data, i);
    i += Borsh.writeArray(scopePriceChain, _data, i);
    i += Borsh.writeArray(name, _data, i);
    putInt64LE(_data, i, maxAgePriceSeconds);
    i += 8;
    putInt64LE(_data, i, maxAgeTwapSeconds);
    i += 8;
    putInt64LE(_data, i, maxIgnorableAmountAsReward);
    i += 8;
    _data[i] = (byte) disabled;
    ++i;
    i += Borsh.writeArray(padding0, _data, i);
    i += Borsh.writeArray(scopeStakingRateChain, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}