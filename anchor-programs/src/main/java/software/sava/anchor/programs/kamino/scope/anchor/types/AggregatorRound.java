package software.sava.anchor.programs.kamino.scope.anchor.types;

import software.sava.core.accounts.PublicKey;
import software.sava.core.borsh.Borsh;

import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;

public record AggregatorRound(int numSuccess,
                              int numError,
                              boolean isClosed,
                              long roundOpenSlot,
                              long roundOpenTimestamp,
                              SwitchboardDecimal result,
                              SwitchboardDecimal stdDeviation,
                              SwitchboardDecimal minResponse,
                              SwitchboardDecimal maxResponse,
                              PublicKey[] oraclePubkeysData,
                              SwitchboardDecimal[] mediansData,
                              long[] currentPayout,
                              boolean[] mediansFulfilled,
                              boolean[] errorsFulfilled) implements Borsh {

  public static final int BYTES = 1097;
  public static final int ORACLE_PUBKEYS_DATA_LEN = 16;
  public static final int MEDIANS_DATA_LEN = 16;
  public static final int CURRENT_PAYOUT_LEN = 16;
  public static final int MEDIANS_FULFILLED_LEN = 16;
  public static final int ERRORS_FULFILLED_LEN = 16;

  public static AggregatorRound read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var numSuccess = getInt32LE(_data, i);
    i += 4;
    final var numError = getInt32LE(_data, i);
    i += 4;
    final var isClosed = _data[i] == 1;
    ++i;
    final var roundOpenSlot = getInt64LE(_data, i);
    i += 8;
    final var roundOpenTimestamp = getInt64LE(_data, i);
    i += 8;
    final var result = SwitchboardDecimal.read(_data, i);
    i += Borsh.len(result);
    final var stdDeviation = SwitchboardDecimal.read(_data, i);
    i += Borsh.len(stdDeviation);
    final var minResponse = SwitchboardDecimal.read(_data, i);
    i += Borsh.len(minResponse);
    final var maxResponse = SwitchboardDecimal.read(_data, i);
    i += Borsh.len(maxResponse);
    final var oraclePubkeysData = new PublicKey[16];
    i += Borsh.readArray(oraclePubkeysData, _data, i);
    final var mediansData = new SwitchboardDecimal[16];
    i += Borsh.readArray(mediansData, SwitchboardDecimal::read, _data, i);
    final var currentPayout = new long[16];
    i += Borsh.readArray(currentPayout, _data, i);
    final var mediansFulfilled = new boolean[16];
    i += Borsh.readArray(mediansFulfilled, _data, i);
    final var errorsFulfilled = new boolean[16];
    Borsh.readArray(errorsFulfilled, _data, i);
    return new AggregatorRound(numSuccess,
                               numError,
                               isClosed,
                               roundOpenSlot,
                               roundOpenTimestamp,
                               result,
                               stdDeviation,
                               minResponse,
                               maxResponse,
                               oraclePubkeysData,
                               mediansData,
                               currentPayout,
                               mediansFulfilled,
                               errorsFulfilled);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    putInt32LE(_data, i, numSuccess);
    i += 4;
    putInt32LE(_data, i, numError);
    i += 4;
    _data[i] = (byte) (isClosed ? 1 : 0);
    ++i;
    putInt64LE(_data, i, roundOpenSlot);
    i += 8;
    putInt64LE(_data, i, roundOpenTimestamp);
    i += 8;
    i += Borsh.write(result, _data, i);
    i += Borsh.write(stdDeviation, _data, i);
    i += Borsh.write(minResponse, _data, i);
    i += Borsh.write(maxResponse, _data, i);
    i += Borsh.writeArray(oraclePubkeysData, _data, i);
    i += Borsh.writeArray(mediansData, _data, i);
    i += Borsh.writeArray(currentPayout, _data, i);
    i += Borsh.writeArray(mediansFulfilled, _data, i);
    i += Borsh.writeArray(errorsFulfilled, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
