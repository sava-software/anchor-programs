package software.sava.anchor.programs.drift.anchor.types;

import java.math.BigInteger;

import java.util.OptionalInt;
import java.util.OptionalLong;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;

public record LpPoolParams(OptionalLong maxSettleQuoteAmount,
                           OptionalLong volatility,
                           OptionalInt gammaExecution,
                           OptionalInt xi,
                           BigInteger maxAum,
                           PublicKey whitelistMint) implements Borsh {

  public static LpPoolParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var maxSettleQuoteAmount = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (maxSettleQuoteAmount.isPresent()) {
      i += 8;
    }
    final var volatility = _data[i++] == 0 ? OptionalLong.empty() : OptionalLong.of(getInt64LE(_data, i));
    if (volatility.isPresent()) {
      i += 8;
    }
    final var gammaExecution = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (gammaExecution.isPresent()) {
      ++i;
    }
    final var xi = _data[i++] == 0 ? OptionalInt.empty() : OptionalInt.of(_data[i] & 0xFF);
    if (xi.isPresent()) {
      ++i;
    }
    final var maxAum = _data[i++] == 0 ? null : getInt128LE(_data, i);
    if (maxAum != null) {
      i += 16;
    }
    final var whitelistMint = _data[i++] == 0 ? null : readPubKey(_data, i);
    return new LpPoolParams(maxSettleQuoteAmount,
                            volatility,
                            gammaExecution,
                            xi,
                            maxAum,
                            whitelistMint);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.writeOptional(maxSettleQuoteAmount, _data, i);
    i += Borsh.writeOptional(volatility, _data, i);
    i += Borsh.writeOptionalbyte(gammaExecution, _data, i);
    i += Borsh.writeOptionalbyte(xi, _data, i);
    i += Borsh.write128Optional(maxAum, _data, i);
    i += Borsh.writeOptional(whitelistMint, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return (maxSettleQuoteAmount == null || maxSettleQuoteAmount.isEmpty() ? 1 : (1 + 8))
         + (volatility == null || volatility.isEmpty() ? 1 : (1 + 8))
         + (gammaExecution == null || gammaExecution.isEmpty() ? 1 : (1 + 1))
         + (xi == null || xi.isEmpty() ? 1 : (1 + 1))
         + (maxAum == null ? 1 : (1 + 16))
         + (whitelistMint == null ? 1 : (1 + 32));
  }
}
