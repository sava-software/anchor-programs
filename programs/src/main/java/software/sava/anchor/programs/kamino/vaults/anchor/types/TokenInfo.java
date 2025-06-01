package software.sava.anchor.programs.kamino.vaults.anchor.types;

import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record TokenInfo(// UTF-8 encoded name of the token (null-terminated)
                        byte[] name,
                        // Heuristics limits of acceptable price
                        PriceHeuristic heuristic,
                        // Max divergence between twap and price in bps
                        long maxTwapDivergenceBps,
                        long maxAgePriceSeconds,
                        long maxAgeTwapSeconds,
                        // Scope price configuration
                        ScopeConfiguration scopeConfiguration,
                        // Switchboard configuration
                        SwitchboardConfiguration switchboardConfiguration,
                        // Pyth configuration
                        PythConfiguration pythConfiguration,
                        int blockPriceUsage,
                        byte[] reserved,
                        long[] padding) implements Borsh {

  public static final int BYTES = 384;

  public static TokenInfo read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var name = new byte[32];
    i += Borsh.readArray(name, _data, i);
    final var heuristic = PriceHeuristic.read(_data, i);
    i += Borsh.len(heuristic);
    final var maxTwapDivergenceBps = getInt64LE(_data, i);
    i += 8;
    final var maxAgePriceSeconds = getInt64LE(_data, i);
    i += 8;
    final var maxAgeTwapSeconds = getInt64LE(_data, i);
    i += 8;
    final var scopeConfiguration = ScopeConfiguration.read(_data, i);
    i += Borsh.len(scopeConfiguration);
    final var switchboardConfiguration = SwitchboardConfiguration.read(_data, i);
    i += Borsh.len(switchboardConfiguration);
    final var pythConfiguration = PythConfiguration.read(_data, i);
    i += Borsh.len(pythConfiguration);
    final var blockPriceUsage = _data[i] & 0xFF;
    ++i;
    final var reserved = new byte[7];
    i += Borsh.readArray(reserved, _data, i);
    final var padding = new long[19];
    Borsh.readArray(padding, _data, i);
    return new TokenInfo(name,
                         heuristic,
                         maxTwapDivergenceBps,
                         maxAgePriceSeconds,
                         maxAgeTwapSeconds,
                         scopeConfiguration,
                         switchboardConfiguration,
                         pythConfiguration,
                         blockPriceUsage,
                         reserved,
                         padding);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeArray(name, _data, i);
    i += Borsh.write(heuristic, _data, i);
    putInt64LE(_data, i, maxTwapDivergenceBps);
    i += 8;
    putInt64LE(_data, i, maxAgePriceSeconds);
    i += 8;
    putInt64LE(_data, i, maxAgeTwapSeconds);
    i += 8;
    i += Borsh.write(scopeConfiguration, _data, i);
    i += Borsh.write(switchboardConfiguration, _data, i);
    i += Borsh.write(pythConfiguration, _data, i);
    _data[i] = (byte) blockPriceUsage;
    ++i;
    i += Borsh.writeArray(reserved, _data, i);
    i += Borsh.writeArray(padding, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
