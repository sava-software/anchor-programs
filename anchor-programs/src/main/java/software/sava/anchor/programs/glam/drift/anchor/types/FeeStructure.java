package software.sava.anchor.programs.glam.drift.anchor.types;

import software.sava.core.borsh.Borsh;

public record FeeStructure(EntryExitFees vault,
                           EntryExitFees manager,
                           ManagementFee management,
                           PerformanceFee performance,
                           ProtocolFees protocol) implements Borsh {

  public static final int BYTES = 19;

  public static FeeStructure read(final byte[] _data, final int offset) {
    if (_data == null || _data.length == 0) {
      return null;
    }
    int i = offset;
    final var vault = EntryExitFees.read(_data, i);
    i += Borsh.len(vault);
    final var manager = EntryExitFees.read(_data, i);
    i += Borsh.len(manager);
    final var management = ManagementFee.read(_data, i);
    i += Borsh.len(management);
    final var performance = PerformanceFee.read(_data, i);
    i += Borsh.len(performance);
    final var protocol = ProtocolFees.read(_data, i);
    return new FeeStructure(vault,
                            manager,
                            management,
                            performance,
                            protocol);
  }

  @Override
  public int write(final byte[] _data, final int offset) {
    int i = offset;
    i += Borsh.write(vault, _data, i);
    i += Borsh.write(manager, _data, i);
    i += Borsh.write(management, _data, i);
    i += Borsh.write(performance, _data, i);
    i += Borsh.write(protocol, _data, i);
    return i - offset;
  }

  @Override
  public int l() {
    return BYTES;
  }
}
