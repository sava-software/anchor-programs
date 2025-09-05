package software.sava.anchor.programs.glam.protocol.anchor;

import java.util.List;

import software.sava.anchor.programs.glam.protocol.anchor.types.EmergencyAccessUpdateArgs;
import software.sava.anchor.programs.glam.protocol.anchor.types.EngineField;
import software.sava.anchor.programs.glam.protocol.anchor.types.ExtraParams;
import software.sava.anchor.programs.glam.protocol.anchor.types.JupiterSwapPolicy;
import software.sava.anchor.programs.glam.protocol.anchor.types.PriceDenom;
import software.sava.anchor.programs.glam.protocol.anchor.types.StateModel;
import software.sava.core.accounts.PublicKey;
import software.sava.core.accounts.SolanaAccounts;
import software.sava.core.accounts.meta.AccountMeta;
import software.sava.core.borsh.Borsh;
import software.sava.core.programs.Discriminator;
import software.sava.core.tx.Instruction;

import static java.util.Objects.requireNonNullElse;

import static software.sava.core.accounts.PublicKey.readPubKey;
import static software.sava.core.accounts.meta.AccountMeta.createRead;
import static software.sava.core.accounts.meta.AccountMeta.createReadOnlySigner;
import static software.sava.core.accounts.meta.AccountMeta.createWritableSigner;
import static software.sava.core.accounts.meta.AccountMeta.createWrite;
import static software.sava.core.encoding.ByteUtil.getInt16LE;
import static software.sava.core.encoding.ByteUtil.getInt32LE;
import static software.sava.core.encoding.ByteUtil.getInt64LE;
import static software.sava.core.encoding.ByteUtil.putInt16LE;
import static software.sava.core.encoding.ByteUtil.putInt32LE;
import static software.sava.core.encoding.ByteUtil.putInt64LE;
import static software.sava.core.programs.Discriminator.createAnchorDiscriminator;
import static software.sava.core.programs.Discriminator.toDiscriminator;

public final class GlamProtocolProgram {

  public static final Discriminator CLOSE_STATE_DISCRIMINATOR = toDiscriminator(25, 1, 184, 101, 200, 245, 210, 246);

  public static Instruction closeState(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey metadataKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createWrite(requireNonNullElse(metadataKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, CLOSE_STATE_DISCRIMINATOR);
  }

  public static final Discriminator CPI_PROXY_DISCRIMINATOR = toDiscriminator(65, 134, 48, 2, 7, 232, 199, 46);

  // Only accessible by integration programs
  public static Instruction cpiProxy(final AccountMeta invokedGlamProtocolProgramMeta,
                                     final PublicKey glamStateKey,
                                     final PublicKey glamVaultKey,
                                     final PublicKey glamSignerKey,
                                     final PublicKey cpiProgramKey,
                                     final PublicKey integrationAuthorityKey,
                                     final byte[] data,
                                     final ExtraParams[] extraParams) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createReadOnlySigner(integrationAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(data) + Borsh.lenVector(extraParams)];
    int i = CPI_PROXY_DISCRIMINATOR.write(_data, 0);
    i += Borsh.writeVector(data, _data, i);
    Borsh.writeVector(extraParams, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record CpiProxyIxData(Discriminator discriminator, byte[] data, ExtraParams[] extraParams) implements Borsh {  

    public static CpiProxyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static CpiProxyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = Borsh.readbyteVector(_data, i);
      i += Borsh.lenVector(data);
      final var extraParams = Borsh.readVector(ExtraParams.class, ExtraParams::read, _data, i);
      return new CpiProxyIxData(discriminator, data, extraParams);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(data, _data, i);
      i += Borsh.writeVector(extraParams, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(data) + Borsh.lenVector(extraParams);
    }
  }

  public static final Discriminator EMERGENCY_UPDATE_STATE_DISCRIMINATOR = toDiscriminator(156, 211, 55, 70, 92, 37, 190, 66);

  // Bypasses the timelock for emergency updates on access control. Allowed operations:
  // - removing an integration program
  // - removing a delegate
  // - enabling/disabling glam state
  public static Instruction emergencyUpdateState(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamSignerKey,
                                                 final EmergencyAccessUpdateArgs args) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(args)];
    int i = EMERGENCY_UPDATE_STATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(args, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record EmergencyUpdateStateIxData(Discriminator discriminator, EmergencyAccessUpdateArgs args) implements Borsh {  

    public static EmergencyUpdateStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static EmergencyUpdateStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var args = EmergencyAccessUpdateArgs.read(_data, i);
      return new EmergencyUpdateStateIxData(discriminator, args);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(args, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(args);
    }
  }

  public static final Discriminator ENABLE_DISABLE_PROTOCOLS_DISCRIMINATOR = toDiscriminator(222, 198, 164, 163, 194, 161, 11, 171);

  public static Instruction enableDisableProtocols(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamSignerKey,
                                                   final PublicKey integrationProgram,
                                                   final int protocolsBitmask,
                                                   final boolean setEnabled) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[43];
    int i = ENABLE_DISABLE_PROTOCOLS_DISCRIMINATOR.write(_data, 0);
    integrationProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolsBitmask);
    i += 2;
    _data[i] = (byte) (setEnabled ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record EnableDisableProtocolsIxData(Discriminator discriminator,
                                             PublicKey integrationProgram,
                                             int protocolsBitmask,
                                             boolean setEnabled) implements Borsh {  

    public static EnableDisableProtocolsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 43;

    public static EnableDisableProtocolsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var integrationProgram = readPubKey(_data, i);
      i += 32;
      final var protocolsBitmask = getInt16LE(_data, i);
      i += 2;
      final var setEnabled = _data[i] == 1;
      return new EnableDisableProtocolsIxData(discriminator, integrationProgram, protocolsBitmask, setEnabled);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      integrationProgram.write(_data, i);
      i += 32;
      putInt16LE(_data, i, protocolsBitmask);
      i += 2;
      _data[i] = (byte) (setEnabled ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator EXTEND_STATE_DISCRIMINATOR = toDiscriminator(34, 147, 151, 206, 134, 128, 82, 228);

  public static Instruction extendState(final AccountMeta invokedGlamProtocolProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamSignerKey,
                                        final int bytes) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[12];
    int i = EXTEND_STATE_DISCRIMINATOR.write(_data, 0);
    putInt32LE(_data, i, bytes);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record ExtendStateIxData(Discriminator discriminator, int bytes) implements Borsh {  

    public static ExtendStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static ExtendStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var bytes = getInt32LE(_data, i);
      return new ExtendStateIxData(discriminator, bytes);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt32LE(_data, i, bytes);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator GRANT_REVOKE_DELEGATE_PERMISSIONS_DISCRIMINATOR = toDiscriminator(162, 21, 218, 157, 218, 86, 114, 171);

  public static Instruction grantRevokeDelegatePermissions(final AccountMeta invokedGlamProtocolProgramMeta,
                                                           final PublicKey glamStateKey,
                                                           final PublicKey glamSignerKey,
                                                           final PublicKey delegate,
                                                           final PublicKey integrationProgram,
                                                           final int protocolBitflag,
                                                           final long permissionsBitmask,
                                                           final boolean setGranted) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[83];
    int i = GRANT_REVOKE_DELEGATE_PERMISSIONS_DISCRIMINATOR.write(_data, 0);
    delegate.write(_data, i);
    i += 32;
    integrationProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolBitflag);
    i += 2;
    putInt64LE(_data, i, permissionsBitmask);
    i += 8;
    _data[i] = (byte) (setGranted ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record GrantRevokeDelegatePermissionsIxData(Discriminator discriminator,
                                                     PublicKey delegate,
                                                     PublicKey integrationProgram,
                                                     int protocolBitflag,
                                                     long permissionsBitmask,
                                                     boolean setGranted) implements Borsh {  

    public static GrantRevokeDelegatePermissionsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 83;

    public static GrantRevokeDelegatePermissionsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var delegate = readPubKey(_data, i);
      i += 32;
      final var integrationProgram = readPubKey(_data, i);
      i += 32;
      final var protocolBitflag = getInt16LE(_data, i);
      i += 2;
      final var permissionsBitmask = getInt64LE(_data, i);
      i += 8;
      final var setGranted = _data[i] == 1;
      return new GrantRevokeDelegatePermissionsIxData(discriminator,
                                                      delegate,
                                                      integrationProgram,
                                                      protocolBitflag,
                                                      permissionsBitmask,
                                                      setGranted);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      delegate.write(_data, i);
      i += 32;
      integrationProgram.write(_data, i);
      i += 32;
      putInt16LE(_data, i, protocolBitflag);
      i += 2;
      putInt64LE(_data, i, permissionsBitmask);
      i += 8;
      _data[i] = (byte) (setGranted ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator INITIALIZE_STATE_DISCRIMINATOR = toDiscriminator(190, 171, 224, 219, 217, 72, 199, 176);

  public static Instruction initializeState(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey baseAssetMintKey,
                                            final StateModel state) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.systemProgram()),
      createRead(baseAssetMintKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = INITIALIZE_STATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record InitializeStateIxData(Discriminator discriminator, StateModel state) implements Borsh {  

    public static InitializeStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static InitializeStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var state = StateModel.read(_data, i);
      return new InitializeStateIxData(discriminator, state);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(state, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(state);
    }
  }

  public static final Discriminator JUPITER_SWAP_DISCRIMINATOR = toDiscriminator(116, 207, 0, 196, 252, 120, 243, 18);

  public static Instruction jupiterSwap(final AccountMeta invokedGlamProtocolProgramMeta,
                                        final SolanaAccounts solanaAccounts,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamVaultKey,
                                        final PublicKey glamSignerKey,
                                        final PublicKey cpiProgramKey,
                                        final PublicKey inputVaultAtaKey,
                                        final PublicKey outputVaultAtaKey,
                                        final PublicKey inputMintKey,
                                        final PublicKey outputMintKey,
                                        final PublicKey inputStakePoolKey,
                                        final PublicKey outputStakePoolKey,
                                        final PublicKey inputTokenProgramKey,
                                        final PublicKey outputTokenProgramKey,
                                        final byte[] data) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(cpiProgramKey),
      createWrite(inputVaultAtaKey),
      createWrite(outputVaultAtaKey),
      createRead(inputMintKey),
      createRead(outputMintKey),
      createRead(requireNonNullElse(inputStakePoolKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(outputStakePoolKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(solanaAccounts.associatedTokenAccountProgram()),
      createRead(inputTokenProgramKey),
      createRead(outputTokenProgramKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(data)];
    int i = JUPITER_SWAP_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record JupiterSwapIxData(Discriminator discriminator, byte[] data) implements Borsh {  

    public static JupiterSwapIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static JupiterSwapIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var data = Borsh.readbyteVector(_data, i);
      return new JupiterSwapIxData(discriminator, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(data);
    }
  }

  public static final Discriminator LINK_UNLINK_MINT_DISCRIMINATOR = toDiscriminator(237, 235, 138, 232, 220, 182, 115, 14);

  // For glam mint program's use only
  public static Instruction linkUnlinkMint(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamMintKey,
                                           final PublicKey glamMintAuthorityKey,
                                           final boolean link) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamMintKey),
      createReadOnlySigner(glamMintAuthorityKey)
    );

    final byte[] _data = new byte[9];
    int i = LINK_UNLINK_MINT_DISCRIMINATOR.write(_data, 0);
    _data[i] = (byte) (link ? 1 : 0);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record LinkUnlinkMintIxData(Discriminator discriminator, boolean link) implements Borsh {  

    public static LinkUnlinkMintIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static LinkUnlinkMintIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var link = _data[i] == 1;
      return new LinkUnlinkMintIxData(discriminator, link);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      _data[i] = (byte) (link ? 1 : 0);
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_DRIFT_USERS_DISCRIMINATOR = toDiscriminator(12, 5, 143, 51, 101, 81, 200, 150);

  // Extra accounts for pricing N drift users under the same user stats:
  // - user_stats x 1
  // - drift_user x N
  // - markets and oracles used by all drift users (no specific order)
  public static Instruction priceDriftUsers(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey signerKey,
                                            final PublicKey solOracleKey,
                                            final PublicKey glamConfigKey,
                                            final PriceDenom denom,
                                            final int numUsers) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[9 + Borsh.len(denom)];
    int i = PRICE_DRIFT_USERS_DISCRIMINATOR.write(_data, 0);
    i += Borsh.write(denom, _data, i);
    _data[i] = (byte) numUsers;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceDriftUsersIxData(Discriminator discriminator, PriceDenom denom, int numUsers) implements Borsh {  

    public static PriceDriftUsersIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static PriceDriftUsersIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      i += Borsh.len(denom);
      final var numUsers = _data[i] & 0xFF;
      return new PriceDriftUsersIxData(discriminator, denom, numUsers);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      _data[i] = (byte) numUsers;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR = toDiscriminator(234, 16, 238, 70, 189, 23, 98, 160);

  // Extra accounts for pricing N vault depositors:
  // - (vault_depositor, drift_vault, drift_user) x N
  // - spot_market used by drift users of vaults (no specific order)
  // - perp markets used by drift users of vaults (no specific order)
  // - oracles of spot markets and perp markets (no specific order)
  public static Instruction priceDriftVaultDepositors(final AccountMeta invokedGlamProtocolProgramMeta,
                                                      final PublicKey glamStateKey,
                                                      final PublicKey glamVaultKey,
                                                      final PublicKey signerKey,
                                                      final PublicKey solOracleKey,
                                                      final PublicKey glamConfigKey,
                                                      final PriceDenom denom,
                                                      final int numVaultDepositors,
                                                      final int numSpotMarkets,
                                                      final int numPerpMarkets) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[11 + Borsh.len(denom)];
    int i = PRICE_DRIFT_VAULT_DEPOSITORS_DISCRIMINATOR.write(_data, 0);
    i += Borsh.write(denom, _data, i);
    _data[i] = (byte) numVaultDepositors;
    ++i;
    _data[i] = (byte) numSpotMarkets;
    ++i;
    _data[i] = (byte) numPerpMarkets;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceDriftVaultDepositorsIxData(Discriminator discriminator,
                                                PriceDenom denom,
                                                int numVaultDepositors,
                                                int numSpotMarkets,
                                                int numPerpMarkets) implements Borsh {  

    public static PriceDriftVaultDepositorsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 12;

    public static PriceDriftVaultDepositorsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      i += Borsh.len(denom);
      final var numVaultDepositors = _data[i] & 0xFF;
      ++i;
      final var numSpotMarkets = _data[i] & 0xFF;
      ++i;
      final var numPerpMarkets = _data[i] & 0xFF;
      return new PriceDriftVaultDepositorsIxData(discriminator,
                                                 denom,
                                                 numVaultDepositors,
                                                 numSpotMarkets,
                                                 numPerpMarkets);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      _data[i] = (byte) numVaultDepositors;
      ++i;
      _data[i] = (byte) numSpotMarkets;
      ++i;
      _data[i] = (byte) numPerpMarkets;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR = toDiscriminator(166, 110, 234, 179, 240, 179, 69, 246);

  public static Instruction priceKaminoObligations(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey kaminoLendingProgramKey,
                                                   final PublicKey solOracleKey,
                                                   final PublicKey glamConfigKey,
                                                   final PublicKey pythOracleKey,
                                                   final PublicKey switchboardPriceOracleKey,
                                                   final PublicKey switchboardTwapOracleKey,
                                                   final PublicKey scopePricesKey,
                                                   final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(kaminoLendingProgramKey),
      createRead(solOracleKey),
      createRead(glamConfigKey),
      createRead(requireNonNullElse(pythOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardPriceOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(switchboardTwapOracleKey, invokedGlamProtocolProgramMeta.publicKey())),
      createRead(requireNonNullElse(scopePricesKey, invokedGlamProtocolProgramMeta.publicKey()))
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = PRICE_KAMINO_OBLIGATIONS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceKaminoObligationsIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceKaminoObligationsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceKaminoObligationsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceKaminoObligationsIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR = toDiscriminator(112, 92, 238, 224, 145, 105, 38, 249);

  // Price Kamino vault shares.
  // - `num_vaults` Number of kamino vaults to price.
  // 
  // Extra accounts for pricing N kamino vault shares:
  // - (kvault_share_ata, kvault_share_mint, kvault_state, kvault_deposit_token_oracle) x N
  // - reserve x M
  // - M = number of reserves used by all kvaults' allocations
  // - reserve pubkeys must follow the same order of reserves used by each allocation
  public static Instruction priceKaminoVaultShares(final AccountMeta invokedGlamProtocolProgramMeta,
                                                   final PublicKey glamStateKey,
                                                   final PublicKey glamVaultKey,
                                                   final PublicKey signerKey,
                                                   final PublicKey solOracleKey,
                                                   final PublicKey glamConfigKey,
                                                   final PriceDenom denom,
                                                   final int numVaults) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[9 + Borsh.len(denom)];
    int i = PRICE_KAMINO_VAULT_SHARES_DISCRIMINATOR.write(_data, 0);
    i += Borsh.write(denom, _data, i);
    _data[i] = (byte) numVaults;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceKaminoVaultSharesIxData(Discriminator discriminator, PriceDenom denom, int numVaults) implements Borsh {  

    public static PriceKaminoVaultSharesIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 10;

    public static PriceKaminoVaultSharesIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      i += Borsh.len(denom);
      final var numVaults = _data[i] & 0xFF;
      return new PriceKaminoVaultSharesIxData(discriminator, denom, numVaults);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      _data[i] = (byte) numVaults;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_STAKE_ACCOUNTS_DISCRIMINATOR = toDiscriminator(119, 137, 9, 15, 196, 73, 30, 27);

  public static Instruction priceStakeAccounts(final AccountMeta invokedGlamProtocolProgramMeta,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey signerKey,
                                               final PublicKey solOracleKey,
                                               final PublicKey glamConfigKey,
                                               final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = PRICE_STAKE_ACCOUNTS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceStakeAccountsIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceStakeAccountsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceStakeAccountsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceStakeAccountsIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator PRICE_VAULT_TOKENS_DISCRIMINATOR = toDiscriminator(54, 42, 16, 199, 20, 183, 50, 137);

  // Price vault SOL balance and tokens it holds.
  // 
  // Extra accounts for pricing N tokens:
  // - (ata, mint, oracle) x N
  public static Instruction priceVaultTokens(final AccountMeta invokedGlamProtocolProgramMeta,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamVaultKey,
                                             final PublicKey signerKey,
                                             final PublicKey solOracleKey,
                                             final PublicKey glamConfigKey,
                                             final PriceDenom denom) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createRead(glamVaultKey),
      createWritableSigner(signerKey),
      createRead(solOracleKey),
      createRead(glamConfigKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(denom)];
    int i = PRICE_VAULT_TOKENS_DISCRIMINATOR.write(_data, 0);
    Borsh.write(denom, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record PriceVaultTokensIxData(Discriminator discriminator, PriceDenom denom) implements Borsh {  

    public static PriceVaultTokensIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 9;

    public static PriceVaultTokensIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var denom = PriceDenom.read(_data, i);
      return new PriceVaultTokensIxData(discriminator, denom);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(denom, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SET_JUPITER_SWAP_POLICY_DISCRIMINATOR = toDiscriminator(189, 182, 227, 165, 127, 148, 246, 189);

  public static Instruction setJupiterSwapPolicy(final AccountMeta invokedGlamProtocolProgramMeta,
                                                 final PublicKey glamStateKey,
                                                 final PublicKey glamVaultKey,
                                                 final PublicKey glamSignerKey,
                                                 final JupiterSwapPolicy policy) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(policy)];
    int i = SET_JUPITER_SWAP_POLICY_DISCRIMINATOR.write(_data, 0);
    Borsh.write(policy, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SetJupiterSwapPolicyIxData(Discriminator discriminator, JupiterSwapPolicy policy) implements Borsh {  

    public static SetJupiterSwapPolicyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetJupiterSwapPolicyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var policy = JupiterSwapPolicy.read(_data, i);
      return new SetJupiterSwapPolicyIxData(discriminator, policy);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(policy, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(policy);
    }
  }

  public static final Discriminator SET_PROTOCOL_POLICY_DISCRIMINATOR = toDiscriminator(37, 99, 61, 122, 227, 102, 182, 180);

  public static Instruction setProtocolPolicy(final AccountMeta invokedGlamProtocolProgramMeta,
                                              final PublicKey glamStateKey,
                                              final PublicKey glamSignerKey,
                                              final PublicKey integrationProgram,
                                              final int protocolBitflag,
                                              final byte[] data) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[42 + Borsh.lenVector(data)];
    int i = SET_PROTOCOL_POLICY_DISCRIMINATOR.write(_data, 0);
    integrationProgram.write(_data, i);
    i += 32;
    putInt16LE(_data, i, protocolBitflag);
    i += 2;
    Borsh.writeVector(data, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SetProtocolPolicyIxData(Discriminator discriminator,
                                        PublicKey integrationProgram,
                                        int protocolBitflag,
                                        byte[] data) implements Borsh {  

    public static SetProtocolPolicyIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static SetProtocolPolicyIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var integrationProgram = readPubKey(_data, i);
      i += 32;
      final var protocolBitflag = getInt16LE(_data, i);
      i += 2;
      final var data = Borsh.readbyteVector(_data, i);
      return new SetProtocolPolicyIxData(discriminator, integrationProgram, protocolBitflag, data);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      integrationProgram.write(_data, i);
      i += 32;
      putInt16LE(_data, i, protocolBitflag);
      i += 2;
      i += Borsh.writeVector(data, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + 32 + 2 + Borsh.lenVector(data);
    }
  }

  public static final Discriminator STAKE_AUTHORIZE_DISCRIMINATOR = toDiscriminator(127, 247, 88, 164, 201, 0, 79, 7);

  // Out-of-scope for audit
  public static Instruction stakeAuthorize(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey stakeKey,
                                           final PublicKey newAuthority,
                                           final int stakerOrWithdrawer) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar())
    );

    final byte[] _data = new byte[44];
    int i = STAKE_AUTHORIZE_DISCRIMINATOR.write(_data, 0);
    newAuthority.write(_data, i);
    i += 32;
    putInt32LE(_data, i, stakerOrWithdrawer);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeAuthorizeIxData(Discriminator discriminator, PublicKey newAuthority, int stakerOrWithdrawer) implements Borsh {  

    public static StakeAuthorizeIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 44;

    public static StakeAuthorizeIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var newAuthority = readPubKey(_data, i);
      i += 32;
      final var stakerOrWithdrawer = getInt32LE(_data, i);
      return new StakeAuthorizeIxData(discriminator, newAuthority, stakerOrWithdrawer);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      newAuthority.write(_data, i);
      i += 32;
      putInt32LE(_data, i, stakerOrWithdrawer);
      i += 4;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_DEACTIVATE_DISCRIMINATOR = toDiscriminator(224, 10, 93, 175, 175, 145, 237, 169);

  // Out-of-scope for audit
  public static Instruction stakeDeactivate(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_DEACTIVATE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_DELEGATE_STAKE_DISCRIMINATOR = toDiscriminator(202, 40, 152, 239, 175, 251, 66, 228);

  // Out-of-scope for audit
  public static Instruction stakeDelegateStake(final AccountMeta invokedGlamProtocolProgramMeta,
                                               final SolanaAccounts solanaAccounts,
                                               final PublicKey glamStateKey,
                                               final PublicKey glamVaultKey,
                                               final PublicKey glamSignerKey,
                                               final PublicKey stakeKey,
                                               final PublicKey voteKey,
                                               final PublicKey stakeConfigKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(voteKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(stakeConfigKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_DELEGATE_STAKE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_INITIALIZE_DISCRIMINATOR = toDiscriminator(68, 66, 118, 79, 15, 144, 190, 190);

  // Out-of-scope for audit
  public static Instruction stakeInitialize(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.rentSysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_INITIALIZE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_MERGE_DISCRIMINATOR = toDiscriminator(46, 181, 125, 12, 51, 179, 134, 176);

  // Out-of-scope for audit
  public static Instruction stakeMerge(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey destinationStakeKey,
                                       final PublicKey sourceStakeKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(destinationStakeKey),
      createWrite(sourceStakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar())
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_MERGE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_REDELEGATE_DISCRIMINATOR = toDiscriminator(134, 227, 164, 247, 120, 0, 225, 174);

  // Out-of-scope for audit
  public static Instruction stakeRedelegate(final AccountMeta invokedGlamProtocolProgramMeta,
                                            final SolanaAccounts solanaAccounts,
                                            final PublicKey glamStateKey,
                                            final PublicKey glamVaultKey,
                                            final PublicKey glamSignerKey,
                                            final PublicKey stakeKey,
                                            final PublicKey newStakeKey,
                                            final PublicKey voteKey,
                                            final PublicKey stakeConfigKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createWrite(newStakeKey),
      createRead(voteKey),
      createRead(stakeConfigKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, STAKE_REDELEGATE_DISCRIMINATOR);
  }

  public static final Discriminator STAKE_SPLIT_DISCRIMINATOR = toDiscriminator(63, 128, 169, 206, 158, 60, 135, 48);

  // Out-of-scope for audit
  public static Instruction stakeSplit(final AccountMeta invokedGlamProtocolProgramMeta,
                                       final SolanaAccounts solanaAccounts,
                                       final PublicKey glamStateKey,
                                       final PublicKey glamVaultKey,
                                       final PublicKey glamSignerKey,
                                       final PublicKey stakeKey,
                                       final PublicKey splitStakeKey,
                                       final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createWrite(splitStakeKey)
    );

    final byte[] _data = new byte[16];
    int i = STAKE_SPLIT_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeSplitIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeSplitIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeSplitIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeSplitIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator STAKE_WITHDRAW_DISCRIMINATOR = toDiscriminator(199, 13, 168, 20, 92, 151, 29, 56);

  // Out-of-scope for audit
  public static Instruction stakeWithdraw(final AccountMeta invokedGlamProtocolProgramMeta,
                                          final SolanaAccounts solanaAccounts,
                                          final PublicKey glamStateKey,
                                          final PublicKey glamVaultKey,
                                          final PublicKey glamSignerKey,
                                          final PublicKey stakeKey,
                                          final long lamports) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.stakeProgram()),
      createWrite(stakeKey),
      createRead(solanaAccounts.clockSysVar()),
      createRead(solanaAccounts.stakeHistorySysVar()),
      createRead(solanaAccounts.systemProgram())
    );

    final byte[] _data = new byte[16];
    int i = STAKE_WITHDRAW_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record StakeWithdrawIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static StakeWithdrawIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static StakeWithdrawIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new StakeWithdrawIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator SYSTEM_TRANSFER_DISCRIMINATOR = toDiscriminator(167, 164, 195, 155, 219, 152, 191, 230);

  public static Instruction systemTransfer(final AccountMeta invokedGlamProtocolProgramMeta,
                                           final SolanaAccounts solanaAccounts,
                                           final PublicKey glamStateKey,
                                           final PublicKey glamVaultKey,
                                           final PublicKey glamSignerKey,
                                           final PublicKey toKey,
                                           final long lamports) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWritableSigner(glamSignerKey),
      createRead(solanaAccounts.systemProgram()),
      createWrite(toKey)
    );

    final byte[] _data = new byte[16];
    int i = SYSTEM_TRANSFER_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, lamports);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record SystemTransferIxData(Discriminator discriminator, long lamports) implements Borsh {  

    public static SystemTransferIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 16;

    public static SystemTransferIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var lamports = getInt64LE(_data, i);
      return new SystemTransferIxData(discriminator, lamports);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, lamports);
      i += 8;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator TOKEN_TRANSFER_CHECKED_BY_MINT_AUTHORITY_DISCRIMINATOR = toDiscriminator(37, 131, 188, 85, 45, 183, 8, 81);

  // For glam mint program's use only
  public static Instruction tokenTransferCheckedByMintAuthority(final AccountMeta invokedGlamProtocolProgramMeta,
                                                                final PublicKey glamStateKey,
                                                                final PublicKey glamVaultKey,
                                                                final PublicKey glamMintKey,
                                                                final PublicKey glamMintAuthorityKey,
                                                                final PublicKey fromKey,
                                                                final PublicKey toKey,
                                                                final PublicKey mintKey,
                                                                final PublicKey tokenProgramKey,
                                                                final long amount,
                                                                final int decimals) {
    final var keys = List.of(
      createRead(glamStateKey),
      createWrite(glamVaultKey),
      createWrite(glamMintKey),
      createWritableSigner(glamMintAuthorityKey),
      createWrite(fromKey),
      createWrite(toKey),
      createRead(mintKey),
      createRead(tokenProgramKey)
    );

    final byte[] _data = new byte[17];
    int i = TOKEN_TRANSFER_CHECKED_BY_MINT_AUTHORITY_DISCRIMINATOR.write(_data, 0);
    putInt64LE(_data, i, amount);
    i += 8;
    _data[i] = (byte) decimals;

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record TokenTransferCheckedByMintAuthorityIxData(Discriminator discriminator, long amount, int decimals) implements Borsh {  

    public static TokenTransferCheckedByMintAuthorityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static final int BYTES = 17;

    public static TokenTransferCheckedByMintAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var amount = getInt64LE(_data, i);
      i += 8;
      final var decimals = _data[i] & 0xFF;
      return new TokenTransferCheckedByMintAuthorityIxData(discriminator, amount, decimals);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      putInt64LE(_data, i, amount);
      i += 8;
      _data[i] = (byte) decimals;
      ++i;
      return i - offset;
    }

    @Override
    public int l() {
      return BYTES;
    }
  }

  public static final Discriminator UPDATE_MINT_PARAMS_DISCRIMINATOR = toDiscriminator(45, 42, 115, 25, 179, 27, 57, 191);

  public static Instruction updateMintParams(final AccountMeta invokedGlamProtocolProgramMeta,
                                             final PublicKey glamStateKey,
                                             final PublicKey glamSignerKey,
                                             final EngineField[] params) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(params)];
    int i = UPDATE_MINT_PARAMS_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(params, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record UpdateMintParamsIxData(Discriminator discriminator, EngineField[] params) implements Borsh {  

    public static UpdateMintParamsIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMintParamsIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = Borsh.readVector(EngineField.class, EngineField::read, _data, i);
      return new UpdateMintParamsIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(params);
    }
  }

  public static final Discriminator UPDATE_MINT_PARAMS_BY_MINT_AUTHORITY_DISCRIMINATOR = toDiscriminator(94, 160, 55, 53, 175, 225, 62, 118);

  // For glam mint program's use only
  public static Instruction updateMintParamsByMintAuthority(final AccountMeta invokedGlamProtocolProgramMeta,
                                                            final PublicKey glamStateKey,
                                                            final PublicKey glamMintKey,
                                                            final PublicKey glamMintAuthorityKey,
                                                            final EngineField[] params) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWrite(glamMintKey),
      createReadOnlySigner(glamMintAuthorityKey)
    );

    final byte[] _data = new byte[8 + Borsh.lenVector(params)];
    int i = UPDATE_MINT_PARAMS_BY_MINT_AUTHORITY_DISCRIMINATOR.write(_data, 0);
    Borsh.writeVector(params, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record UpdateMintParamsByMintAuthorityIxData(Discriminator discriminator, EngineField[] params) implements Borsh {  

    public static UpdateMintParamsByMintAuthorityIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateMintParamsByMintAuthorityIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var params = Borsh.readVector(EngineField.class, EngineField::read, _data, i);
      return new UpdateMintParamsByMintAuthorityIxData(discriminator, params);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.writeVector(params, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.lenVector(params);
    }
  }

  public static final Discriminator UPDATE_STATE_DISCRIMINATOR = toDiscriminator(135, 112, 215, 75, 247, 185, 53, 176);

  public static Instruction updateState(final AccountMeta invokedGlamProtocolProgramMeta,
                                        final PublicKey glamStateKey,
                                        final PublicKey glamSignerKey,
                                        final StateModel state) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    final byte[] _data = new byte[8 + Borsh.len(state)];
    int i = UPDATE_STATE_DISCRIMINATOR.write(_data, 0);
    Borsh.write(state, _data, i);

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, _data);
  }

  public record UpdateStateIxData(Discriminator discriminator, StateModel state) implements Borsh {  

    public static UpdateStateIxData read(final Instruction instruction) {
      return read(instruction.data(), instruction.offset());
    }

    public static UpdateStateIxData read(final byte[] _data, final int offset) {
      if (_data == null || _data.length == 0) {
        return null;
      }
      final var discriminator = createAnchorDiscriminator(_data, offset);
      int i = offset + discriminator.length();
      final var state = StateModel.read(_data, i);
      return new UpdateStateIxData(discriminator, state);
    }

    @Override
    public int write(final byte[] _data, final int offset) {
      int i = offset + discriminator.write(_data, offset);
      i += Borsh.write(state, _data, i);
      return i - offset;
    }

    @Override
    public int l() {
      return 8 + Borsh.len(state);
    }
  }

  public static final Discriminator UPDATE_STATE_APPLY_TIMELOCK_DISCRIMINATOR = toDiscriminator(66, 12, 138, 80, 133, 85, 46, 220);

  public static Instruction updateStateApplyTimelock(final AccountMeta invokedGlamProtocolProgramMeta, final PublicKey glamStateKey, final PublicKey glamSignerKey) {
    final var keys = List.of(
      createWrite(glamStateKey),
      createWritableSigner(glamSignerKey)
    );

    return Instruction.createInstruction(invokedGlamProtocolProgramMeta, keys, UPDATE_STATE_APPLY_TIMELOCK_DISCRIMINATOR);
  }

  private GlamProtocolProgram() {
  }
}
