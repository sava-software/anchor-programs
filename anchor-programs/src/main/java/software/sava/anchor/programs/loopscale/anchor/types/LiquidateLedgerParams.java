package software.sava.anchor.programs.loopscale.anchor.types;

import software.sava.core.borsh.Borsh;

public record LiquidateLedgerParams(int ledgerIndex,
                                    boolean unwrapSol,
                                    byte[] assetIndexGuidance) implements Borsh {

  public static LiquidateLedgerParams read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var ledgerIndex = _data[i] & 0xFF;
    ++i;
    final var unwrapSol = _data[i] == 1;
    ++i;
    final var assetIndexGuidance = Borsh.readbyteVector(_data, i);
    return new LiquidateLedgerParams(ledgerIndex, unwrapSol, assetIndexGuidance);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    _data[i] = (byte) ledgerIndex;
    ++i;
    _data[i] = (byte) (unwrapSol ? 1 : 0);
    ++i;
    i += Borsh.writeVector(assetIndexGuidance, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return 1 + 1 + Borsh.lenVector(assetIndexGuidance);
  }
}
