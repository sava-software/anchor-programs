package software.sava.anchor.programs.jito.tip_router.anchor.types;

import software.sava.core.borsh.Borsh;

public record EpochAccountStatus(int epochState,
                                 int weightTable,
                                 int epochSnapshot,
                                 byte[] operatorSnapshot,
                                 int ballotBox,
                                 int baseRewardRouter,
                                 byte[] ncnRewardRouter) implements Borsh {

  public static final int BYTES = 2309;

  public static EpochAccountStatus read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var epochState = _data[i] & 0xFF;
    ++i;
    final var weightTable = _data[i] & 0xFF;
    ++i;
    final var epochSnapshot = _data[i] & 0xFF;
    ++i;
    final var operatorSnapshot = new byte[256];
    i += Borsh.readArray(operatorSnapshot, _data, i);
    final var ballotBox = _data[i] & 0xFF;
    ++i;
    final var baseRewardRouter = _data[i] & 0xFF;
    ++i;
    final var ncnRewardRouter = new byte[2048];
    Borsh.readArray(ncnRewardRouter, _data, i);
    return new EpochAccountStatus(epochState,
                                  weightTable,
                                  epochSnapshot,
                                  operatorSnapshot,
                                  ballotBox,
                                  baseRewardRouter,
                                  ncnRewardRouter);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) epochState;
    ++i;
    _data[i] = (byte) weightTable;
    ++i;
    _data[i] = (byte) epochSnapshot;
    ++i;
    i += Borsh.writeArray(operatorSnapshot, _data, i);
    _data[i] = (byte) ballotBox;
    ++i;
    _data[i] = (byte) baseRewardRouter;
    ++i;
    i += Borsh.writeArray(ncnRewardRouter, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
