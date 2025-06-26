package software.sava.anchor.programs.metadao.amm.anchor.types;

import java.math.BigInteger;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.encoding.ByteUtil.getInt128LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt128LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record CommonFields(long slot,
                           long unixTimestamp,
                           PublicKey user,
                           PublicKey amm,
                           long postBaseReserves,
                           long postQuoteReserves,
                           BigInteger oracleLastPrice,
                           BigInteger oracleLastObservation,
                           BigInteger oracleAggregator,
                           long seqNum) implements Borsh {

  public static final int BYTES = 152;

  public static CommonFields read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var slot = getInt64LE(_data, i);
    i += 8;
    final var unixTimestamp = getInt64LE(_data, i);
    i += 8;
    final var user = readPubKey(_data, i);
    i += 32;
    final var amm = readPubKey(_data, i);
    i += 32;
    final var postBaseReserves = getInt64LE(_data, i);
    i += 8;
    final var postQuoteReserves = getInt64LE(_data, i);
    i += 8;
    final var oracleLastPrice = getInt128LE(_data, i);
    i += 16;
    final var oracleLastObservation = getInt128LE(_data, i);
    i += 16;
    final var oracleAggregator = getInt128LE(_data, i);
    i += 16;
    final var seqNum = getInt64LE(_data, i);
    return new CommonFields(slot,
                            unixTimestamp,
                            user,
                            amm,
                            postBaseReserves,
                            postQuoteReserves,
                            oracleLastPrice,
                            oracleLastObservation,
                            oracleAggregator,
                            seqNum);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt64LE(_data, i, slot);
    i += 8;
    putInt64LE(_data, i, unixTimestamp);
    i += 8;
    user.write(_data, i);
    i += 32;
    amm.write(_data, i);
    i += 32;
    putInt64LE(_data, i, postBaseReserves);
    i += 8;
    putInt64LE(_data, i, postQuoteReserves);
    i += 8;
    putInt128LE(_data, i, oracleLastPrice);
    i += 16;
    putInt128LE(_data, i, oracleLastObservation);
    i += 16;
    putInt128LE(_data, i, oracleAggregator);
    i += 16;
    putInt64LE(_data, i, seqNum);
    i += 8;
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
